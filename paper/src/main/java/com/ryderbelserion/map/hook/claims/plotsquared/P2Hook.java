package com.ryderbelserion.map.hook.claims.plotsquared;

import com.ryderbelserion.map.util.ChunkUtil;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.util.ConfigUtil;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.marker.Polygon;
import net.pl3x.map.core.markers.option.Fill;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Colors;
import net.pl3x.map.core.world.World;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class P2Hook implements Listener, Hook {

    public P2Hook() {
        P2Config.reload();
    }

    @Override
    public void registerWorld(@NotNull final World world) {
        world.getLayerRegistry().register(new P2Layer(this, world));
    }

    @Override
    public void unloadWorld(@NotNull final World world) {
        world.getLayerRegistry().unregister(P2Layer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull final World world) {
        if (!ConfigUtil.isClaimsEnabled()) return EMPTY_LIST;

        final Collection<Marker<?>> markers = new ArrayList<>();

        for (final PlotArea plotArea : PlotSquared.get().getPlotAreaManager().getPlotAreasSet(world.getName())) {
            final Map<String, Boolean> visitedRegions = new HashMap<>();

            for (final Plot plot : plotArea.getPlots()) {
                final String key = String.format("p2_%s_%s", world.getName(), plot.getId());
                final Collection<P2Plot> p2plots = new HashSet<>();

                for (final CuboidRegion region : plot.getRegions()) {
                    if (visitedRegions.putIfAbsent(region.toString(), true) != null) {
                        continue; // dont draw regions we've already drawn...
                    }

                    final BlockVector3 max = region.getMaximumPoint();
                    final BlockVector3 min = region.getMinimumPoint();

                    p2plots.add(new P2Plot(min.getX() - 1, max.getX() + 1, min.getZ() - 1, max.getZ() + 1, null));
                }

                if (p2plots.isEmpty()) {
                    continue; // no p2plots added
                }

                final Polygon poly = ChunkUtil.getPoly(key, p2plots);

                markers.add(poly.setOptions(options(world, plot.getOwner())));
            }
        }

        return markers;
    }

    private @NotNull Options.Builder options(@NotNull final World world, @Nullable final UUID owner) {
        final OfflinePlayer player = owner == null ? null : Bukkit.getOfflinePlayer(owner);  //todo() wtf
        final String ownerName = player == null || player.getName() == null ? "unknown" : player.getName();

        return Options.builder()
                .strokeColor(Colors.fromHex(P2Config.MARKER_STROKE_COLOR))
                .strokeWeight(P2Config.MARKER_STROKE_WEIGHT)
                .fillColor(Colors.fromHex(P2Config.MARKER_FILL_COLOR))
                .fillType(Fill.Type.NONZERO)
                .popupContent(P2Config.MARKER_POPUP
                        .replace("<world>", world.getName())
                        .replace("<owner>", ownerName)
                );
    }
}
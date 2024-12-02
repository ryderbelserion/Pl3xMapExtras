package com.ryderbelserion.map.hook.claims.worldguard;

import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.util.ConfigUtil;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionType;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Colors;
import net.pl3x.map.core.world.World;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldGuardHook implements Hook {

    public WorldGuardHook() {
        WorldGuardConfig.reload();
    }

    private @Nullable RegionManager getRegionManager(@NotNull World world) {
        org.bukkit.World bukkit = Bukkit.getWorld(world.getName());

        return bukkit == null ? null : WorldGuard.getInstance().getPlatform()
                .getRegionContainer().get(BukkitAdapter.adapt(bukkit));
    }

    @Override
    public void registerWorld(@NotNull World world) {
        if (getRegionManager(world) != null) {
            world.getLayerRegistry().register(new WorldGuardLayer(this, world));
        }
    }

    @Override
    public void unloadWorld(@NotNull World world) {
        world.getLayerRegistry().unregister(WorldGuardLayer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull World world) {
        if (!ConfigUtil.isClaimsEnabled()) return EMPTY_LIST;

        RegionManager manager = getRegionManager(world);

        if (manager == null) {
            return EMPTY_LIST;
        }

        return manager.getRegions().values().stream()
                .filter(region -> !WorldGuardConfig.BLACKLISTED_REGIONS.contains(region.getId()))
                .map(region -> new WorldGuardClaim(world, region))
                .filter(claim -> claim.getType() == RegionType.CUBOID || claim.getType() == RegionType.POLYGON)
                .map(claim -> {
                    String key = "wg-claim-" + claim.getID();
                    Marker<?> marker;
                    if (claim.getType() == RegionType.POLYGON) {
                        marker = Marker.polygon(key, Marker.polyline(key + "line",
                                claim.getPoints().stream().map(point ->
                                        Point.of(point.getX(), point.getZ())).toList()));
                    } else {
                        marker = Marker.rectangle(key, claim.getMin(), claim.getMax());
                    }

                    return marker.setOptions(getOptions(claim));
                })
                .collect(Collectors.toSet());
    }

    private @NotNull Options getOptions(@NotNull WorldGuardClaim claim) {
        return Options.builder()
                .strokeWeight(WorldGuardConfig.MARKER_STROKE_WEIGHT)
                .strokeColor(Colors.fromHex(WorldGuardConfig.MARKER_STROKE_COLOR))
                .fillColor(Colors.fromHex(WorldGuardConfig.MARKER_FILL_COLOR))
                .popupContent(processPopup(WorldGuardConfig.MARKER_POPUP, claim))
                .build();
    }

    private @NotNull String processPopup(@NotNull String popup, @NotNull WorldGuardClaim claim) {
        return popup.replace("<world>", claim.getWorld().getName())
                .replace("<regionname>", claim.getID())
                .replace("<owners>", getOwners(claim))
                .replace("<members>", getMembers(claim))
                .replace("<parent>", claim.getParent() == null ? "" : claim.getParent().getId())
                .replace("<priority>", String.valueOf(claim.getPriority()))
                .replace("<flags>", getFlags(claim));
    }

    private @NotNull String getOwners(@NotNull WorldGuardClaim claim) {
        Set<String> set = new HashSet<>();
        set.addAll(claim.getOwners().getPlayers());
        set.addAll(claim.getOwners().getGroups());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_OWNERS
                .replace("<owners>", String.join(", ", set));
    }

    private @NotNull String getMembers(@NotNull WorldGuardClaim claim) {
        Set<String> set = new HashSet<>();
        set.addAll(claim.getMembers().getPlayers());
        set.addAll(claim.getMembers().getGroups());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_MEMBERS
                .replace("<members>", String.join(", ", set));
    }

    private @NotNull String getFlags(@NotNull WorldGuardClaim claim) {
        Map<Flag<?>, Object> flags = claim.getFlags();
        Set<String> set = flags.keySet().stream()
                .map(flag -> WorldGuardConfig.MARKER_POPUP_FLAGS_ENTRY
                        .replace("<flag>", flag.getName())
                        .replace("<value>", String.valueOf(flags.get(flag))))
                .collect(Collectors.toSet());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_FLAGS
                .replace("<flags>", String.join("<br/>", set));
    }
}
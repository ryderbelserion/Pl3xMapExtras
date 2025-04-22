package com.ryderbelserion.map.anchors.types;

import com.ryderbelserion.map.anchors.Anchor;
import com.ryderbelserion.map.hook.claims.worldguard.WorldGuardClaim;
import com.ryderbelserion.map.hook.claims.worldguard.WorldGuardConfig;
import com.ryderbelserion.map.hook.claims.worldguard.WorldGuardLayer;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionType;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Colors;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class WorldGuardAnchor extends Anchor {

    @Override
    public void registerWorld(@NotNull final World world) {
        if (getRegionManager(world).isEmpty()) return;

        //world.getLayerRegistry().register(new WorldGuardLayer(this, world));
    }

    @Override
    public void deleteWorld(@NotNull final World world) {
        world.getLayerRegistry().unregister(WorldGuardLayer.KEY);
    }

    @Override
    public @NotNull final Optional<Collection<Marker<?>>> process(@NotNull final World world) {
        return getRegionManager(world).map(regionManager -> regionManager.getRegions().values().stream()
                .filter(region -> !WorldGuardConfig.BLACKLISTED_REGIONS.contains(region.getId()))
                .map(region -> new WorldGuardClaim(world, region))
                .filter(claim -> claim.getType() == RegionType.CUBOID || claim.getType() == RegionType.POLYGON)
                .map(claim -> {
                    final String key = "wg-claim-" + claim.getID();

                    Marker<?> marker;

                    if (claim.getType() == RegionType.POLYGON) {
                        marker = Marker.polygon(key, Marker.polyline(key + "line", claim.getPoints().stream().map(point -> Point.of(point.getX(), point.getZ())).toList()));
                    } else {
                        marker = Marker.rectangle(key, claim.getMin(), claim.getMax());
                    }

                    return marker.setOptions(getOptions(claim));
                })
                .collect(Collectors.toSet()));
    }

    private @NotNull Optional<RegionManager> getRegionManager(@NotNull final World world) {
        final Optional<org.bukkit.World> bukkitWorld = Optional.ofNullable(this.plugin.getServer().getWorld(world.getName()));

        if (bukkitWorld.isEmpty()) return Optional.empty();

        final RegionManager instance = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(bukkitWorld.get()));

        return Optional.ofNullable(instance);
    }

    private @NotNull Options getOptions(@NotNull final WorldGuardClaim claim) {
        return Options.builder()
                .strokeWeight(WorldGuardConfig.MARKER_STROKE_WEIGHT)
                .strokeColor(Colors.fromHex(WorldGuardConfig.MARKER_STROKE_COLOR))
                .fillColor(Colors.fromHex(WorldGuardConfig.MARKER_FILL_COLOR))
                .popupContent(processPopup(WorldGuardConfig.MARKER_POPUP, claim))
                .build();
    }

    private @NotNull String processPopup(@NotNull final String popup, @NotNull final WorldGuardClaim claim) {
        return popup.replace("<world>", claim.getWorld().getName())
                .replace("<regionname>", claim.getID())
                .replace("<owners>", getOwners(claim))
                .replace("<members>", getMembers(claim))
                .replace("<parent>", claim.getParent() == null ? "" : claim.getParent().getId())
                .replace("<priority>", String.valueOf(claim.getPriority()))
                .replace("<flags>", getFlags(claim));
    }

    private @NotNull String getOwners(@NotNull final WorldGuardClaim claim) {
        final Set<String> set = new HashSet<>();
        set.addAll(claim.getOwners().getPlayers());
        set.addAll(claim.getOwners().getGroups());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_OWNERS
                .replace("<owners>", String.join(", ", set));
    }

    private @NotNull String getMembers(@NotNull final WorldGuardClaim claim) {
        final Set<String> set = new HashSet<>();
        set.addAll(claim.getMembers().getPlayers());
        set.addAll(claim.getMembers().getGroups());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_MEMBERS
                .replace("<members>", String.join(", ", set));
    }

    private @NotNull String getFlags(@NotNull final WorldGuardClaim claim) {
        final Map<Flag<?>, Object> flags = claim.getFlags();

        final Set<String> set = flags.keySet().stream()
                .map(flag -> WorldGuardConfig.MARKER_POPUP_FLAGS_ENTRY
                        .replace("<flag>", flag.getName())
                        .replace("<value>", String.valueOf(flags.get(flag))))
                .collect(Collectors.toSet());

        return set.isEmpty() ? "" : WorldGuardConfig.MARKER_POPUP_FLAGS
                .replace("<flags>", String.join("<br/>", set));
    }
}
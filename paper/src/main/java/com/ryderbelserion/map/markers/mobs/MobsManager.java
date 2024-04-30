package com.ryderbelserion.map.markers.mobs;

import com.ryderbelserion.map.config.MobConfig;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MobsManager {

    private final Map<String, Collection<Marker<?>>> activeMarkers = new ConcurrentHashMap<>();

    public Collection<Marker<?>> getActiveMarkers(@NotNull String worldName) {
        Collection<Marker<?>> markers = this.activeMarkers.get(worldName);

        if (markers != null) {
            return markers;
        }

        return Collections.emptySet();
    }

    public void clearMarkers(@NotNull String worldName) {
        if (this.activeMarkers.isEmpty() || worldName.isEmpty() || worldName.isBlank()) return;

        this.activeMarkers.remove(worldName);
    }

    public void clearAll() {
        this.activeMarkers.clear();
    }

    public void addWorld(@NotNull String worldName) {
        if (this.activeMarkers.containsKey(worldName)) return;

        this.activeMarkers.put(worldName, new HashSet<>());
    }

    public void addMarker(@NotNull String key, @NotNull Mob mob, @NotNull MobConfig config) {
        net.pl3x.map.core.markers.marker.Icon icon = getIcon(key, mob, config);

        // Remove it if it exists.
        removeMarker(mob);

        // Add new icon.
        this.activeMarkers.get(mob.getWorld().getName()).add(icon);
    }

    public void removeMarker(@NotNull Mob mob) {
        String worldName = mob.getWorld().getName();

        String key = String.format("%s_%s_%s", "pl3xmap_mobs", worldName, mob.getUniqueId());

        this.activeMarkers.get(worldName).removeIf(marker -> marker.getKey().equals(key));
    }

    private @NotNull String mob(@NotNull Mob mob) {
        String name = mob.getCustomName();

        return name == null ? mob.getName() : name;
    }

    private @NotNull Point point(@NotNull Location loc) {
        return Point.of(loc.getBlockX(), loc.getBlockZ());
    }

    public net.pl3x.map.core.markers.marker.Icon getIcon(String key, Mob mob, MobConfig config) {
        return Marker.icon(key, point(mob.getLocation()), Icon.get(mob).getKey(), config.ICON_SIZE)
                .setOptions(Options.builder()
                        .tooltipDirection(Tooltip.Direction.TOP)
                        .tooltipContent(config.ICON_TOOLTIP_CONTENT.replace("<mob-id>", mob(mob)))
                        .build());
    }
}
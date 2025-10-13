package com.ryderbelserion.map.marker.mobs;

import com.ryderbelserion.map.config.MobConfig;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import org.bukkit.Location;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MobsManager {

    private final Map<String, Collection<Marker<?>>> activeMarkers = new ConcurrentHashMap<>();

    public Collection<Marker<?>> getActiveMarkers(@NotNull final String worldName) {
        final Collection<Marker<?>> markers = this.activeMarkers.get(worldName);

        if (markers != null) {
            return markers;
        }

        return Collections.emptySet();
    }

    public void clearMarkers(@NotNull final String worldName) {
        if (this.activeMarkers.isEmpty() || worldName.isBlank()) return;

        this.activeMarkers.remove(worldName);
    }

    public void clearAll() {
        this.activeMarkers.clear();
    }

    public void addWorld(@NotNull final String worldName) {
        if (this.activeMarkers.containsKey(worldName)) return;

        this.activeMarkers.put(worldName, new HashSet<>());
    }

    public void addMarker(@NotNull final String key, @NotNull final Mob mob, @NotNull final MobConfig config) {
        final net.pl3x.map.core.markers.marker.Icon icon = getIcon(key, mob, config);

        if (icon == null) { // return if null.
            return;
        }

        // Remove it if it exists.
        removeMarker(mob);

        // Add new icon.
        this.activeMarkers.get(mob.getWorld().getName()).add(icon);
    }

    public void removeMarker(@NotNull final Mob mob) {
        final String worldName = mob.getWorld().getName();

        final String key = String.format("%s_%s_%s", "pl3xmap_mobs", worldName, mob.getUniqueId());

        if (!this.activeMarkers.containsKey(worldName)) return;

        this.activeMarkers.get(worldName).removeIf(marker -> marker.getKey().equals(key));
    }

    private @NotNull String mob(@NotNull final Mob mob) {
        @Nullable final Component name = mob.customName();

        return name == null ? mob.getName() : PlainTextComponentSerializer.plainText().serialize(name);
    }

    private @NotNull Point point(@NotNull final Location loc) {
        return Point.of(loc.getBlockX(), loc.getBlockZ());
    }

    public @Nullable net.pl3x.map.core.markers.marker.Icon getIcon(@NotNull final String key, @NotNull final Mob mob, @NotNull final MobConfig config) {
        return Icon.get(mob).map(value -> Marker.icon(key, point(mob.getLocation()), value.getKey(), config.ICON_SIZE)
                .setOptions(Options.builder()
                        .tooltipDirection(Tooltip.Direction.TOP)
                        .tooltipContent(config.ICON_TOOLTIP_CONTENT.replace("<mob-id>", mob(mob)))
                        .build())).orElse(null);
    }
}
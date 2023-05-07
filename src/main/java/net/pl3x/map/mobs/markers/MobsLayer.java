/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.pl3x.map.mobs.markers;

import java.util.Collection;
import java.util.HashSet;
import libs.org.checkerframework.checker.nullness.qual.NonNull;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import net.pl3x.map.mobs.configuration.WorldConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Mob;

public class MobsLayer extends WorldLayer {
    public static final String KEY = "pl3xmap_mobs";

    private final WorldConfig config;

    public MobsLayer(@NonNull WorldConfig config) {
        super(KEY, config.getWorld(), () -> config.LAYER_LABEL);
        this.config = config;

        setShowControls(config.LAYER_SHOW_CONTROLS);
        setDefaultHidden(config.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(config.LAYER_UPDATE_INTERVAL);
        setPriority(config.LAYER_PRIORITY);
        setZIndex(config.LAYER_ZINDEX);
    }

    private String mob(Mob mob) {
        @SuppressWarnings("deprecation")
        String name = mob.getCustomName();
        return name == null ? mob.getName() : name;
    }

    private Point point(Location loc) {
        return Point.of(loc.getBlockX(), loc.getBlockZ());
    }

    @Override
    public @NonNull Collection<@NonNull Marker<@NonNull ?>> getMarkers() {
        Collection<Marker<?>> markers = new HashSet<>();
        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            World bukkitWorld = Bukkit.getWorld(world.getName());
            if (bukkitWorld == null) {
                return;
            }
            bukkitWorld.getEntitiesByClass(Mob.class).forEach(mob -> {
                String key = String.format("%s_%s_%s", KEY, getWorld().getName(), mob.getUniqueId());
                markers.add(Marker.icon(key, point(mob.getLocation()), Icon.get(mob).getKey(), this.config.ICON_SIZE)
                        .setOptions(Options.builder()
                                .tooltipDirection(Tooltip.Direction.TOP)
                                .tooltipContent(config.ICON_TOOLTIP_CONTENT
                                        .replace("<mob-id>", mob(mob))
                                ).build()));
            });
        });
        return markers;
    }
}
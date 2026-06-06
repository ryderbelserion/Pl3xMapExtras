package com.ryderbelserion.map.api.objects;

import net.kyori.adventure.key.Key;
import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;

public record MapPosition(Key worldKey, String worldName, int x, int y, int z) {

    public static MapPosition of(@NotNull final Key worldKey, @NotNull final String worldName, final int x, final int y, final int z) {
        return new MapPosition(worldKey, worldName, x, y, z);
    }

    public @NotNull Point asPoint() {
        return Point.of(x(), z());
    }
}
package com.ryderbelserion.map.objects;

import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;

public record MapPosition(String worldName, int x, int y, int z) {

    public static MapPosition of(@NotNull final String worldName, final int x, final int y, final int z) {
        return new MapPosition(worldName, x, y, z);
    }

    public @NotNull Point asPoint() {
        return Point.of(x(), z());
    }
}
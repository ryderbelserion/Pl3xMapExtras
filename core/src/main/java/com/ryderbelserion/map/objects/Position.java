package com.ryderbelserion.map.objects;

import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;

public record Position(int x, int y, int z) {

    public @NotNull Point asPoint() {
        return Point.of(x(), z());
    }
}
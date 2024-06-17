package com.ryderbelserion.map.marker.banners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;

public record Position(int x, int y, int z) {
    public @NotNull Point toPoint() {
        return Point.of(x(), z());
    }

    public static @NotNull Position load(@NotNull DataInputStream in) throws IOException {
        return new Position(in.readInt(), in.readInt(), in.readInt());
    }

    public void save(@NotNull DataOutputStream out) throws IOException {
        out.writeInt(x());
        out.writeInt(y());
        out.writeInt(z());
    }
}

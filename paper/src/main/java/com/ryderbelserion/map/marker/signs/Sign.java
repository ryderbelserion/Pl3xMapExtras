package com.ryderbelserion.map.marker.signs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public record Sign(@NotNull Position pos, @NotNull Icon icon, @NotNull List<String> lines) {

    public boolean isSign(@NotNull final World world) {
        return world.getBlockAt(pos().x(), pos().y(), pos().z()).getState() instanceof org.bukkit.block.Sign;
    }

    public static @NotNull Sign load(@NotNull final DataInputStream in) throws IOException {
        return new Sign(Position.load(in), Icon.valueOf(in.readUTF()), List.of(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF()));
    }

    public void save(@NotNull final DataOutputStream out) throws IOException {
        pos().save(out);

        out.writeUTF(icon().name());

        for (final String line : lines()) {
            out.writeUTF(line);
        }
    }
}
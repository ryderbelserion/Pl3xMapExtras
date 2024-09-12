package com.ryderbelserion.map.marker.banners;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public record Banner(Position pos, Icon icon, String name) {

    public boolean isBanner(@NotNull World world) {
        return world.getBlockAt(pos().x(), pos().y(), pos().z()).getState() instanceof org.bukkit.block.Banner;
    }

    public static @NotNull Banner load(@NotNull DataInputStream in) throws IOException {
        return new Banner(Position.load(in), Icon.valueOf(in.readUTF()), in.readUTF());
    }

    public void save(@NotNull DataOutputStream out) throws IOException {
        pos().save(out);

        out.writeUTF(icon().name());
        out.writeUTF(name());
    }
}
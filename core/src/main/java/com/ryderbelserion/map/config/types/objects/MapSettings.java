package com.ryderbelserion.map.config.types.objects;

import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class MapSettings {

    private final World world;
    private final String label;

    public MapSettings(@NotNull final World world, @NotNull final String label) {
        this.world = world;
        this.label = label;
    }

    public @NotNull final World getWorld() {
        return this.world;
    }

    public @NotNull final String getLabel() {
        return this.label;
    }
}
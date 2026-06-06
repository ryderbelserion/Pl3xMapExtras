package com.ryderbelserion.map.common.utils;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;

public class KeyUtils {

    public static Key asKey(@NotNull final String world) {
        return Key.key(switch (world) {
            case "world" -> "overworld";
            case "world_nether" -> "the_nether";
            case "world_the_end" -> "the_end";
            default -> world;
        });
    }

    public static String asString(@NotNull final Key key) {
        return switch (key.asString()) {
            case "world" -> "overworld";
            case "world_nether" -> "the_nether";
            case "world_the_end" -> "the_end";
            default -> key.value();
        };
    }
}
package com.ryderbelserion.map;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pl3xMapProvider {

    private static @Nullable Pl3xMapCommon instance;

    @ApiStatus.Internal
    public static void register(@NotNull final Pl3xMapCommon instance) {
        Pl3xMapProvider.instance = instance;
    }

    @ApiStatus.Internal
    public static void unregister() {
        Pl3xMapProvider.instance = null;
    }

    public static @NotNull Pl3xMapCommon getInstance() {
        if (instance == null) throw new IllegalStateException("Pl3xMapExtras API is not yet initialized.");

        return instance;
    }
}
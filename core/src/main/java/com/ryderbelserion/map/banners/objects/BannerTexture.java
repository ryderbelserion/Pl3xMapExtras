package com.ryderbelserion.map.banners.objects;

import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public class BannerTexture {

    private final Path path;

    public BannerTexture(@NotNull final Path path) {
        this.path = path;
    }

    public void init() {

    }

    public @NotNull final Path getPath() {
        return this.path;
    }
}
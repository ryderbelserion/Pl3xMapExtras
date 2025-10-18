package com.ryderbelserion.map.banners.objects;

import com.ryderbelserion.map.objects.Position;
import org.jetbrains.annotations.NotNull;

public class Banner {

    private final BannerTexture texture;
    private final Position position;
    private final String worldName;
    private final String name;

    public Banner(@NotNull final BannerTexture texture, @NotNull final Position position, @NotNull final String worldName, @NotNull final String name) {
        this.texture = texture;
        this.position = position;
        this.worldName = worldName;
        this.name = name;
    }

    public @NotNull final BannerTexture getTexture() {
        return texture;
    }

    public @NotNull final Position getPosition() {
        return position;
    }

    public @NotNull final String getWorldName() {
        return this.worldName;
    }

    public @NotNull final String getName() {
        return this.name;
    }
}
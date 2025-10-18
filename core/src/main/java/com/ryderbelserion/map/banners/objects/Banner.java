package com.ryderbelserion.map.banners.objects;

import com.ryderbelserion.map.objects.Position;
import org.jetbrains.annotations.NotNull;

public class Banner {

    private final BannerTexture texture;
    private final Position position;
    private final String worldName;
    private final String bannerName;

    public Banner(@NotNull final BannerTexture texture, @NotNull final String bannerName, @NotNull final String worldName, @NotNull final Position position) {
        this.bannerName = bannerName;
        this.worldName = worldName;
        this.position = position;
        this.texture = texture;
    }

    public @NotNull final BannerTexture getTexture() {
        return texture;
    }

    public @NotNull final String getBannerName() {
        return this.bannerName;
    }

    public @NotNull final Position getPosition() {
        return position;
    }

    public @NotNull final String getWorldName() {
        return this.worldName;
    }
}
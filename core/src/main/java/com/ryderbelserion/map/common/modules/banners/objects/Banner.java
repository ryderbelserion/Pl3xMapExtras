package com.ryderbelserion.map.common.modules.banners.objects;

import com.ryderbelserion.map.api.objects.MapPosition;
import com.ryderbelserion.map.common.objects.MapTexture;
import org.jetbrains.annotations.NotNull;

public record Banner(@NotNull MapTexture texture, @NotNull String bannerName, @NotNull MapPosition position) {

    public static Banner of(@NotNull final MapTexture texture, @NotNull final String bannerName, @NotNull final MapPosition position) {
        return new Banner(texture, bannerName, position);
    }
}
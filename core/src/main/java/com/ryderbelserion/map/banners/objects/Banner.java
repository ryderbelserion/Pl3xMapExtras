package com.ryderbelserion.map.banners.objects;

import com.ryderbelserion.map.objects.MapPosition;
import org.jetbrains.annotations.NotNull;

public record Banner(@NotNull BannerTexture texture, @NotNull String bannerName, @NotNull String worldName, @NotNull MapPosition position) {}
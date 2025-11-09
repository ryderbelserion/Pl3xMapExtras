package com.ryderbelserion.map.modules.banners.objects;

import com.ryderbelserion.map.objects.MapPosition;
import org.jetbrains.annotations.NotNull;

public record Banner(@NotNull BannerTexture texture, @NotNull String bannerName, @NotNull MapPosition position) {}
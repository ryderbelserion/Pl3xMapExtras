package com.ryderbelserion.map.banners.objects;

import org.jetbrains.annotations.NotNull;

public record BannerLocation(@NotNull String worldName, int x, int y, int z) {}
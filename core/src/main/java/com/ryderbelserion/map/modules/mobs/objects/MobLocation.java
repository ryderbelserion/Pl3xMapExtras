package com.ryderbelserion.map.modules.mobs.objects;

import org.jetbrains.annotations.NotNull;

public record MobLocation(@NotNull String worldName, int x, int y, int z) {}
package com.ryderbelserion.map.modules.mobs.objects;

import com.ryderbelserion.map.modules.mobs.interfaces.IMobTexture;
import com.ryderbelserion.map.objects.MapPosition;
import org.jetbrains.annotations.NotNull;

public record Mob(@NotNull IMobTexture texture, @NotNull String mobName, @NotNull String worldName, @NotNull MapPosition position) {}
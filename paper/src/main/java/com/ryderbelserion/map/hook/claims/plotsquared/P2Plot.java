package com.ryderbelserion.map.hook.claims.plotsquared;

import java.util.UUID;
import com.ryderbelserion.map.hook.claims.Region;
import org.jetbrains.annotations.Nullable;

public record P2Plot(int minX, int maxX, int minZ, int maxZ, @Nullable UUID owner) implements Region {}
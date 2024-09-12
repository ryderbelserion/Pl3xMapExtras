package com.ryderbelserion.map.hook.claims.claimchunk;

import java.util.UUID;
import com.ryderbelserion.map.hook.claims.Chunk;
import org.jetbrains.annotations.NotNull;

public record ClaimChunkClaim(int minX, int minZ, @NotNull UUID owner) implements Chunk {
    public boolean isTouching(@NotNull ClaimChunkClaim other) {
        return owner().equals(other.owner()) && ( // same owner
                (other.minX() == minX() && other.minZ() == minZ() - 1) || // touches north
                (other.minX() == minX() && other.minZ() == minZ() + 1) || // touches south
                (other.minX() == minX() - 1 && other.minZ() == minZ()) || // touches west
                (other.minX() == minX() + 1 && other.minZ() == minZ()) // touches east
        );
    }
}
package com.ryderbelserion.map.hook.claims.claimchunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ClaimChunkGroup {

    private final List<ClaimChunkClaim> claims = new ArrayList<>();
    private final UUID owner;

    public ClaimChunkGroup(@NotNull final ClaimChunkClaim claim, @NotNull final UUID owner) {
        add(claim);
        this.owner = owner;
    }

    public boolean isTouching(@NotNull final ClaimChunkClaim claim) {
        for (final ClaimChunkClaim toChk : this.claims) {
            if (toChk.isTouching(claim)) {
                return true;
            }
        }

        return false;
    }

    public boolean isTouching(@NotNull final ClaimChunkGroup group) {
        for (final ClaimChunkClaim claim : group.claims()) {
            if (isTouching(claim)) {
                return true;
            }
        }

        return false;
    }

    public void add(@NotNull final ClaimChunkClaim claim) {
        this.claims.add(claim);
    }

    public void add(@NotNull final ClaimChunkGroup group) {
        this.claims.addAll(group.claims());
    }

    public @NotNull List<ClaimChunkClaim> claims() {
        return this.claims;
    }

    public @NotNull UUID owner() {
        return this.owner;
    }

    public @NotNull String id() {
        if (!this.claims.isEmpty()) {
            final ClaimChunkClaim claim = this.claims.getFirst();

            return claim.minX() + "_" + claim.minZ();
        } else {
            return "NaN_NaN";
        }
    }
}
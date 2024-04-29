package com.ryderbelserion.map.hook.claims.claimchunk;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

public class ClaimChunkGroup {

    private final List<ClaimChunkClaim> claims = new ArrayList<>();
    private final UUID owner;

    public ClaimChunkGroup(@NotNull ClaimChunkClaim claim, @NotNull UUID owner) {
        add(claim);
        this.owner = owner;
    }

    public boolean isTouching(@NotNull ClaimChunkClaim claim) {
        for (ClaimChunkClaim toChk : claims) {
            if (toChk.isTouching(claim)) {
                return true;
            }
        }
        return false;
    }

    public boolean isTouching(@NotNull ClaimChunkGroup group) {
        for (ClaimChunkClaim claim : group.claims()) {
            if (isTouching(claim)) {
                return true;
            }
        }
        return false;
    }

    public void add(@NotNull ClaimChunkClaim claim) {
        claims.add(claim);
    }

    public void add(@NotNull ClaimChunkGroup group) {
        claims.addAll(group.claims());
    }

    public @NotNull List<ClaimChunkClaim> claims() {
        return claims;
    }

    public @NotNull UUID owner() {
        return owner;
    }

    public @NotNull String id() {
        if (!claims.isEmpty()) {
            ClaimChunkClaim claim = claims.get(0);
            return claim.minX() + "_" + claim.minZ();
        } else {
            return "NaN_NaN";
        }
    }
}
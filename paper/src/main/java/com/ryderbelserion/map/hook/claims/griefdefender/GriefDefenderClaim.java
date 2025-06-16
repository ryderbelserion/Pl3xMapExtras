package com.ryderbelserion.map.hook.claims.griefdefender;

import com.griefdefender.api.claim.Claim;
import com.griefdefender.lib.flowpowered.math.vector.Vector3i;
import java.util.UUID;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class GriefDefenderClaim {
    private final World world;
    private final Claim claim;
    private final Point min;
    private final Point max;

    public GriefDefenderClaim(@NotNull final World world, @NotNull final Claim claim) {
        this.world = world;
        this.claim = claim;

        final Vector3i min = this.claim.getLesserBoundaryCorner();
        final Vector3i max = this.claim.getGreaterBoundaryCorner();

        this.min = Point.of(min.getX(), min.getZ());
        this.max = Point.of(max.getX(), max.getZ());
    }

    public @NotNull World getWorld() {
        return this.world;
    }

    public boolean isAdminClaim() {
        return this.claim.isAdminClaim();
    }

    public @NotNull UUID getID() {
        return this.claim.getUniqueId();
    }

    public @NotNull String getOwnerName() {
        return this.claim.getOwnerName();
    }

    public @NotNull Point getMin() {
        return this.min;
    }

    public @NotNull Point getMax() {
        return this.max;
    }

    public int getArea() {
        return this.claim.getArea();
    }

    public int getWidth() {
        return this.claim.getWidth();
    }

    public int getHeight() {
        return this.claim.getHeight();
    }
}
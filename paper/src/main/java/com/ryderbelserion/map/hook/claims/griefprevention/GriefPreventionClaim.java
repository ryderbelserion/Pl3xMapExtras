package com.ryderbelserion.map.hook.claims.griefprevention;

import java.util.ArrayList;
import java.util.UUID;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.world.World;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class GriefPreventionClaim {

    private final World world;
    private final Claim claim;
    private final Point min;
    private final Point max;

    private UUID ownerId;
    private String ownerName;

    public GriefPreventionClaim(@NotNull World world, @NotNull Claim claim) {
        this.world = world;
        this.claim = claim;

        Location min = this.claim.getLesserBoundaryCorner();
        Location max = this.claim.getGreaterBoundaryCorner();
        this.min = Point.of(min.getX(), min.getZ());
        this.max = Point.of(max.getX(), max.getZ());
    }

    public @NotNull World getWorld() {
        return this.world;
    }

    public boolean isAdminClaim() {
        return this.claim.isAdminClaim();
    }

    public @NotNull Long getID() {
        return this.claim.getID();
    }

    public @NotNull String getOwnerName() {
        if (isAdminClaim()) {
            return GriefPrevention.instance.dataStore.getMessage(Messages.OwnerNameForAdminClaims);
        }

        if (this.claim.getOwnerID() != this.ownerId) {
            this.ownerId = this.claim.getOwnerID();
            this.ownerName = this.claim.getOwnerName();
        }

        return this.ownerName;
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

    public void getPermissions(
            @NotNull ArrayList<String> builders,
            @NotNull ArrayList<String> containers,
            @NotNull ArrayList<String> accessors,
            @NotNull ArrayList<String> managers
    ) {
        this.claim.getPermissions(builders, containers, accessors, managers);
    }
}
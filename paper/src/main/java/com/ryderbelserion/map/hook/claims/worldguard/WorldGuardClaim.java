package com.ryderbelserion.map.hook.claims.worldguard;

import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionType;
import java.util.List;
import java.util.Map;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WorldGuardClaim {

    private final World world;
    private final ProtectedRegion region;
    private final Point min;
    private final Point max;

    public WorldGuardClaim(@NotNull World world, @NotNull ProtectedRegion region) {
        this.world = world;
        this.region = region;

        BlockVector3 min = this.region.getMinimumPoint();
        BlockVector3 max = this.region.getMaximumPoint();
        this.min = Point.of(min.getX(), min.getZ());
        this.max = Point.of(max.getX(), max.getZ());
    }

    public @NotNull World getWorld() {
        return this.world;
    }

    public @NotNull String getID() {
        return this.region.getId();
    }

    public @NotNull DefaultDomain getOwners() {
        return this.region.getOwners();
    }

    public @NotNull DefaultDomain getMembers() {
        return this.region.getMembers();
    }

    public @NotNull Point getMin() {
        return this.min;
    }

    public @NotNull Point getMax() {
        return this.max;
    }

    public @Nullable ProtectedRegion getParent() {
        return this.region.getParent();
    }

    public int getPriority() {
        return this.region.getPriority();
    }

    public @NotNull Map<Flag<?>, Object> getFlags() {
        return this.region.getFlags();
    }

    public @NotNull List<BlockVector2> getPoints() {
        return this.region.getPoints();
    }

    public @NotNull RegionType getType() {
        return this.region.getType();
    }
}
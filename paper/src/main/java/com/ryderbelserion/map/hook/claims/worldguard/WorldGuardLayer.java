package com.ryderbelserion.map.hook.claims.worldguard;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class WorldGuardLayer extends WorldLayer {

    public static final String KEY = "worldguard";

    private final WorldGuardHook worldGuardHook;

    public WorldGuardLayer(@NotNull final WorldGuardHook worldGuardHook, @NotNull final World world) {
        super(KEY, world, () -> WorldGuardConfig.LAYER_LABEL);

        this.worldGuardHook = worldGuardHook;

        setShowControls(WorldGuardConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(WorldGuardConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(WorldGuardConfig.LAYER_UPDATE_INTERVAL);
        setPriority(WorldGuardConfig.LAYER_PRIORITY);
        setZIndex(WorldGuardConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.worldGuardHook.getData(getWorld());
    }
}
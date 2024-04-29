package com.ryderbelserion.map.hook.warps.essentials;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class EssentialsLayer extends WorldLayer {
    public static final String KEY = "essentials_warps";

    private final EssentialsHook essentialsHook;

    public EssentialsLayer(@NotNull EssentialsHook essentialsHook, @NotNull World world) {
        super(KEY, world, () -> EssentialsConfig.LAYER_LABEL);
        this.essentialsHook = essentialsHook;

        setShowControls(EssentialsConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(EssentialsConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(EssentialsConfig.LAYER_UPDATE_INTERVAL);
        setPriority(EssentialsConfig.LAYER_PRIORITY);
        setZIndex(EssentialsConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.essentialsHook.getData(getWorld());
    }
}
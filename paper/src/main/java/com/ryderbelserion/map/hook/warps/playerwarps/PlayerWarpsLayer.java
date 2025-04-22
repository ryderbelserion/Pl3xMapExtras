package com.ryderbelserion.map.hook.warps.playerwarps;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class PlayerWarpsLayer extends WorldLayer {
    public static final String KEY = "playerwarps_warps";

    private final PlayerWarpsHook playerWarpsHook;

    public PlayerWarpsLayer(@NotNull final PlayerWarpsHook playerWarpsHook, @NotNull final World world) {
        super(KEY, world, () -> PlayerWarpsConfig.LAYER_LABEL);

        this.playerWarpsHook = playerWarpsHook;

        setShowControls(PlayerWarpsConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(PlayerWarpsConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(PlayerWarpsConfig.LAYER_UPDATE_INTERVAL);
        setPriority(PlayerWarpsConfig.LAYER_PRIORITY);
        setZIndex(PlayerWarpsConfig.LAYER_ZINDEX);
        setCss(PlayerWarpsConfig.LAYER_CSS);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.playerWarpsHook.getData(getWorld());
    }
}
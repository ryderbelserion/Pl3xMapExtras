package com.ryderbelserion.map.hook.claims.griefdefender;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class GriefDefenderLayer extends WorldLayer {

    public static final String KEY = "griefdefender";

    private final GriefDefenderHook griefDefenderHook;

    public GriefDefenderLayer(@NotNull GriefDefenderHook griefDefenderHook, @NotNull World world) {
        super(KEY, world, () -> GriefDefenderConfig.LAYER_LABEL);
        this.griefDefenderHook = griefDefenderHook;

        setShowControls(GriefDefenderConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(GriefDefenderConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(GriefDefenderConfig.LAYER_UPDATE_INTERVAL);
        setPriority(GriefDefenderConfig.LAYER_PRIORITY);
        setZIndex(GriefDefenderConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.griefDefenderHook.getData(getWorld());
    }
}
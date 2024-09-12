package com.ryderbelserion.map.hook.claims.griefprevention;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class GriefPreventionLayer extends WorldLayer {

    public static final String KEY = "griefprevention";

    private final GriefPreventionHook griefPreventionHook;

    public GriefPreventionLayer(@NotNull GriefPreventionHook griefPreventionHook, @NotNull World world) {
        super(KEY, world, () -> GriefPreventionConfig.LAYER_LABEL);
        this.griefPreventionHook = griefPreventionHook;

        setShowControls(GriefPreventionConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(GriefPreventionConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(GriefPreventionConfig.LAYER_UPDATE_INTERVAL);
        setPriority(GriefPreventionConfig.LAYER_PRIORITY);
        setZIndex(GriefPreventionConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.griefPreventionHook.getData(getWorld());
    }
}
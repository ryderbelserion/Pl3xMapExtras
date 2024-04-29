package com.ryderbelserion.map.hook.claims.plotsquared;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class P2Layer extends WorldLayer {

    public static final String KEY = "plotsquared";

    private final P2Hook p2Hook;

    public P2Layer(@NotNull P2Hook p2Hook, @NotNull World world) {
        super(KEY, world, () -> P2Config.LAYER_LABEL);
        this.p2Hook = p2Hook;

        setShowControls(P2Config.LAYER_SHOW_CONTROLS);
        setDefaultHidden(P2Config.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(P2Config.LAYER_UPDATE_INTERVAL);
        setPriority(P2Config.LAYER_PRIORITY);
        setZIndex(P2Config.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.p2Hook.getData(getWorld());
    }
}
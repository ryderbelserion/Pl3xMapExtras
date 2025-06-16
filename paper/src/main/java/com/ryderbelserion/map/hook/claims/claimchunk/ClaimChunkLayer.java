package com.ryderbelserion.map.hook.claims.claimchunk;

import java.util.Collection;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class ClaimChunkLayer extends WorldLayer {

    public static final String KEY = "claimchunk";

    private final ClaimChunkHook claimChunkHook;

    public ClaimChunkLayer(@NotNull final ClaimChunkHook claimChunkHook, @NotNull final World world) {
        super(KEY, world, () -> ClaimChunkConfig.LAYER_LABEL);

        this.claimChunkHook = claimChunkHook;

        setShowControls(ClaimChunkConfig.LAYER_SHOW_CONTROLS);
        setDefaultHidden(ClaimChunkConfig.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(ClaimChunkConfig.LAYER_UPDATE_INTERVAL);
        setPriority(ClaimChunkConfig.LAYER_PRIORITY);
        setZIndex(ClaimChunkConfig.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.claimChunkHook.getData(getWorld());
    }
}
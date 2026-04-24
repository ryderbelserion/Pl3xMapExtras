package com.ryderbelserion.map.api.registry.layers.objects;

import com.ryderbelserion.map.api.objects.MapPosition;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractLayer<M> extends WorldLayer {

    protected final Map<MapPosition, Marker<?>> markers = new ConcurrentHashMap<>();

    protected final World world;

    public AbstractLayer(@NotNull final String key, @NotNull final World world, @NotNull final String label) {
        super(key, this.world = world, () -> label);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.markers.values();
    }

    public abstract Collection<M> getPositions();

    public abstract void refresh();

    public abstract void init();

}
package com.ryderbelserion.map.api.registry.layers;

import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.core.api.enums.Level;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.api.registry.layers.objects.AbstractLayer;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
import net.pl3x.map.core.registry.WorldRegistry;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Optional;

public abstract class AbstractLayerRegistry<L extends AbstractLayer> {

    protected final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();

    protected final FileManager fileManager = this.fusion.getFileManager();

    protected final Path path = this.fusion.getDataPath();

    public abstract void init();

    public abstract void post();

    public void reload() {
        final WorldRegistry registry = Pl3xMap.api().getWorldRegistry();

        final String key = getKey();

        for (final World world : registry.values()) {
            if (!world.isEnabled()) {
                continue;
            }

            final Registry<Layer> layer = world.getLayerRegistry();

            if (!layer.has(key)) {
                continue;
            }

            final String worldName = world.getName();

            if (isEnabled()) {
                getLayer(worldName).ifPresent(AbstractLayer::refresh);

                continue;
            }

            this.fusion.log(Level.WARNING, "Unregistering the %s layer!, as the view is disabled for the world %s".formatted(key, worldName));

            layer.unregister(key);
        }
    }

    public abstract Optional<L> getLayer(@NotNull final String worldName);

    public abstract boolean isEnabled();

    public abstract String getKey();

}
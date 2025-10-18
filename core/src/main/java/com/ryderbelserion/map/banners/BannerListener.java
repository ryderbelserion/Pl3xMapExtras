package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.banners.objects.BannerTexture;
import com.ryderbelserion.map.constants.Namespaces;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class BannerListener implements EventListener {

    private final FusionCore fusion = FusionProvider.getInstance();
    private final BannerRegistry registry;

    public BannerListener(@NotNull final BannerRegistry registry) {
        this.registry = registry;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull final Pl3xMapEnabledEvent event) {
        for (final BannerTexture texture : this.registry.getTextures().values()) {
            texture.register();
        }
    }

    @EventHandler
    public void onServerLoad(@NotNull final ServerLoadedEvent event) {
        for (final BannerTexture texture : this.registry.getTextures().values()) {
            texture.register();
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            world.getLayerRegistry().register(new BannerLayer(world));

            this.fusion.log("warn", "Registered {} on server load", world.getName());
        });
    }

    @EventHandler
    public void onWorldLoad(@NotNull final WorldLoadedEvent event) {
        final World world = event.getWorld();

        world.getLayerRegistry().register(new BannerLayer(world));

        this.fusion.log("warn", "Registered {} on world load", world.getName());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        final World world = event.getWorld();
        final Registry<@NotNull Layer> registry = world.getLayerRegistry();

        if (registry.has(Namespaces.banner_key)) {
            registry.unregister(Namespaces.banner_key);

            this.fusion.log("warn", "Unregistered {} while unloading the world {}", Namespaces.banner_key, world.getName());
        }
    }
}
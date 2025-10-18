package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.constants.Banner;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class BannerListener implements EventListener {

    private final FusionCore fusion;

    public BannerListener(@NotNull final Pl3xMapCommon plugin) {
        this.fusion = plugin.getFusion();

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onServerLoad(@NotNull final ServerLoadedEvent event) {
        Pl3xMap.api().getWorldRegistry().forEach(world -> world.getLayerRegistry().register(new BannerLayer(world)));
    }

    @EventHandler
    public void onWorldLoad(@NotNull final WorldLoadedEvent event) {
        final World world = event.getWorld();

        world.getLayerRegistry().register(new BannerLayer(world));
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        final Registry<@NotNull Layer> registry = event.getWorld().getLayerRegistry();

        if (registry.has(Banner.banner_key)) {
            registry.unregister(Banner.banner_key);
        }
    }
}
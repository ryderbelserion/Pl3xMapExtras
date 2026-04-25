package com.ryderbelserion.map.common.modules.banners;

import com.ryderbelserion.fusion.core.api.enums.Level;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.constants.Namespaces;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.objects.MapTexture;
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

    private final Pl3xMapPlugin plugin = (Pl3xMapPlugin) Pl3xMapExtras.Provider.getInstance();
    private final BannerRegistry registry = this.plugin.getBannerRegistry();
    private final FusionKyori fusion = this.plugin.getFusion();

    private final BannerConfig config = this.plugin.getBannerConfig();

    public BannerListener() {
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull final Pl3xMapEnabledEvent event) {
        if (!this.config.isEnabled()) {
            return;
        }

        for (final MapTexture texture : this.registry.getTextures().values()) {
            texture.register();
        }
    }

    @EventHandler
    public void onServerLoad(@NotNull final ServerLoadedEvent event) {
        if (!this.config.isEnabled()) {
            return;
        }

        for (final MapTexture texture : this.registry.getTextures().values()) {
            texture.register();
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            world.getLayerRegistry().register(new BannerLayer(this.config, world));

            this.fusion.log(Level.WARNING, "Registered layer {} for {} on server load", Namespaces.banner_key, world.getName());
        });
    }

    @EventHandler
    public void onWorldLoad(@NotNull final WorldLoadedEvent event) {
        if (!this.config.isEnabled()) {
            return;
        }

        final World world = event.getWorld();

        world.getLayerRegistry().register(new BannerLayer(this.config, world));

        this.fusion.log(Level.WARNING, "Registered {} on {} load", Namespaces.banner_key, world.getName());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) { // always let this run regardless of config option
        final World world = event.getWorld();
        final Registry<@NotNull Layer> registry = world.getLayerRegistry();

        if (registry.has(Namespaces.banner_key)) {
            registry.unregister(Namespaces.banner_key);

            this.fusion.log(Level.WARNING, "Unregistered {} while unloading the world {}", Namespaces.banner_key, world.getName());
        }
    }
}
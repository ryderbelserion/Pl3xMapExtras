package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.enums.constants.Namespaces;
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

public class MobListener implements EventListener {

    private final FusionCore fusion = FusionProvider.getInstance();
    private final Pl3xMapCommon plugin;
    private final MobRegistry registry;

    public MobListener(@NotNull final Pl3xMapCommon plugin, @NotNull final MobRegistry registry) {
        this.plugin = plugin;
        this.registry = registry;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onServerLoad(@NotNull final ServerLoadedEvent event) {
        if (!this.plugin.getMobConfig().isEnabled()) {
            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            final String worldName = world.getName();

            world.getLayerRegistry().register(new MobLayer(this.plugin, world));

            this.fusion.log("warn", "Registered layer {} for {} on server load", Namespaces.mob_key, worldName);
        });
    }

    @EventHandler
    public void onWorldLoad(@NotNull final WorldLoadedEvent event) {
        if (!this.plugin.getMobConfig().isEnabled()) {
            return;
        }

        final World world = event.getWorld();

        world.getLayerRegistry().register(new MobLayer(this.plugin, world));

        this.fusion.log("warn", "Registered {} on {} load", Namespaces.mob_key, world.getName());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) { // always let this run regardless of config option
        final World world = event.getWorld();
        final Registry<@NotNull Layer> registry = world.getLayerRegistry();

        final String worldName = world.getName();

        if (registry.has(Namespaces.mob_key)) {
            this.registry.removeWorld(worldName);

            registry.unregister(Namespaces.mob_key);

            this.fusion.log("warn", "Unregistered {} while unloading the world {}", Namespaces.mob_key, worldName);
        }
    }
}
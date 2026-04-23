package com.ryderbelserion.map.listener.mobs;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.config.MobConfig;
import com.ryderbelserion.map.marker.mobs.Icon;
import com.ryderbelserion.map.marker.mobs.MobsLayer;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.world.World;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class MobWorldListener implements EventListener, Listener {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Pl3xMapPaper platform = this.plugin.getPlatform();

    private final BasicConfig config = this.platform.getBasicConfig();

    public MobWorldListener() {
        if (!this.config.isMobsEnabled()) return;
        
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (!this.config.isMobsEnabled()) return;
        
        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!this.config.isMobsEnabled()) return;
        
        Icon.register();

        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!this.config.isMobsEnabled()) return;
        
        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!this.config.isMobsEnabled()) return;

        this.platform.getMobsManager().ifPresent(mobs -> {
            mobs.clearMarkers(event.getWorld().getName());

            event.getWorld().getLayerRegistry().unregister(MobsLayer.KEY);
        });
    }

    private void registerWorld(@NotNull final World world) {
        this.platform.getMobsManager().ifPresent(mobs -> {
            // Add new world.
            mobs.addWorld(world.getName());

            // Add new layer.
            world.getLayerRegistry().register(new MobsLayer(new MobConfig(world)));
        });
    }
}
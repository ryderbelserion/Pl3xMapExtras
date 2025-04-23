package com.ryderbelserion.map.listener.mobs;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.config.v1.MobConfig;
import com.ryderbelserion.map.marker.mobs.Icon;
import com.ryderbelserion.map.marker.mobs.MobsLayer;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.world.World;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class MobWorldListener implements EventListener, Listener {

    private @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private @NotNull final Optional<MobsManager> mobsManager = this.plugin.getMobsManager();

    public MobWorldListener() {
        if (this.mobsManager.isEmpty()) return;
        
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (this.mobsManager.isEmpty()) return;
        
        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (this.mobsManager.isEmpty()) return;
        
        Icon.register();

        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (this.mobsManager.isEmpty()) return;
        
        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        //if (!ConfigUtil.isMobsEnabled() || this.mobsManager == null) return;
        
        try {
            // Clear when world is unloaded.
            //this.mobsManager.clearMarkers(event.getWorld().getName());

            // Unregister layer.
            event.getWorld().getLayerRegistry().unregister(MobsLayer.KEY);
        } catch (Throwable ignore) {}
    }

    private void registerWorld(@NotNull final World world) {
        //if (this.mobsManager == null) return;

        // Add new world.
        //this.mobsManager.addWorld(world.getName());

        // Add new layer.
        world.getLayerRegistry().register(new MobsLayer(new MobConfig(world)));
    }
}
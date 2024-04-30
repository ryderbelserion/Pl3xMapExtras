package com.ryderbelserion.map.listener.mobs;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.config.MobConfig;
import com.ryderbelserion.map.markers.mobs.Icon;
import com.ryderbelserion.map.markers.mobs.MobsLayer;
import com.ryderbelserion.map.markers.mobs.MobsManager;
import com.ryderbelserion.map.util.ModuleUtil;
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

public class MobWorldListener implements EventListener, Listener {

    private @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private @NotNull final MobsManager mobsManager = this.plugin.getMobsManager();

    public MobWorldListener() {
        if (!ModuleUtil.isMobsEnabled()) return;
        
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (!ModuleUtil.isMobsEnabled()) return;
        
        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!ModuleUtil.isMobsEnabled()) return;
        
        Icon.register();

        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!ModuleUtil.isMobsEnabled()) return;
        
        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!ModuleUtil.isMobsEnabled()) return;
        
        try {
            // Clear when world is unloaded.
            this.mobsManager.clearMarkers(event.getWorld().getName());

            // Unregister layer.
            event.getWorld().getLayerRegistry().unregister(MobsLayer.KEY);
        } catch (Throwable ignore) {}
    }

    private void registerWorld(@NotNull World world) {
        // Add new world.
        this.mobsManager.addWorld(world.getName());

        // Add new layer.
        world.getLayerRegistry().register(new MobsLayer(new MobConfig(world)));
    }
}
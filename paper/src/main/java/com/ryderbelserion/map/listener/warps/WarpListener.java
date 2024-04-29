package com.ryderbelserion.map.listener.warps;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.hook.Hook;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.world.World;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class WarpListener implements EventListener, Listener {

    private final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    public WarpListener() {
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler
    public void onPluginEnabled(@NotNull PluginEnableEvent event) {
        Hook.add(event.getPlugin().getName());
    }

    @org.bukkit.event.EventHandler
    public void onPluginDisabled(@NotNull PluginDisableEvent event) {
        Hook.remove(event.getPlugin().getName());
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        this.plugin.reload();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        this.plugin.reload();

        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        try {
            Hook.hooks().forEach(hook -> hook.unloadWorld(event.getWorld()));
        } catch (Throwable ignore) {}
    }

    private void registerWorld(@NotNull World world) {
        Hook.hooks().forEach(hook -> hook.registerWorld(world));
    }
}
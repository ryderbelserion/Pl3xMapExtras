package com.ryderbelserion.map.listener.claims;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.hook.Hook;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.jetbrains.annotations.NotNull;

public class ClaimListener implements EventListener, Listener {

    public ClaimListener() {
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
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        Pl3xMap.api().getWorldRegistry().forEach(Pl3xMapExtras::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        Pl3xMapExtras.registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        try {
            Hook.hooks().forEach(hook -> hook.unloadWorld(event.getWorld()));
        } catch (Throwable ignore) {}
    }
}
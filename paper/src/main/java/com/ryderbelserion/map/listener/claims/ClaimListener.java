package com.ryderbelserion.map.listener.claims;

import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.util.ModuleUtil;
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
        if (!ModuleUtil.isClaimsEnabled()) return;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler
    public void onPluginEnabled(@NotNull PluginEnableEvent event) {
        if (!ModuleUtil.isClaimsEnabled()) return;

        Hook.add(event.getPlugin().getName());
    }

    @org.bukkit.event.EventHandler
    public void onPluginDisabled(@NotNull PluginDisableEvent event) {
        if (!ModuleUtil.isClaimsEnabled()) return;

        Hook.remove(event.getPlugin().getName());
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!ModuleUtil.isClaimsEnabled()) return;

        Pl3xMap.api().getWorldRegistry().forEach(ModuleUtil::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!ModuleUtil.isClaimsEnabled()) return;

        ModuleUtil.registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!ModuleUtil.isClaimsEnabled()) return;

        try {
            Hook.hooks().forEach(hook -> hook.unloadWorld(event.getWorld()));
        } catch (Throwable ignore) {}
    }
}
package com.ryderbelserion.map.listener.warps;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.hook.Hook;
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
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.jetbrains.annotations.NotNull;

public class WarpListener implements EventListener, Listener {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Pl3xMapPaper platform = this.plugin.getPlatform();

    private final BasicConfig config = this.platform.getBasicConfig();

    public WarpListener() {
        if (!this.config.isWarpsEnabled()) return;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler
    public void onPluginEnabled(@NotNull PluginEnableEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        Hook.add(event.getPlugin().getName());
    }

    @org.bukkit.event.EventHandler
    public void onPluginDisabled(@NotNull PluginDisableEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        Hook.remove(event.getPlugin().getName());
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        ModuleUtil.reload();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        ModuleUtil.reload();

        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!this.config.isWarpsEnabled()) return;

        try {
            Hook.hooks().forEach(hook -> hook.unloadWorld(event.getWorld()));
        } catch (Throwable ignore) {}
    }

    private void registerWorld(@NotNull final World world) {
        Hook.hooks().forEach(hook -> hook.registerWorld(world));
    }
}
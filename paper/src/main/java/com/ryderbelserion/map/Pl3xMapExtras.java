package com.ryderbelserion.map;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.api.Pl3xMapPlugin;
import com.ryderbelserion.map.listeners.CacheListener;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Pl3xMapExtras extends JavaPlugin {

    private Pl3xMapPlugin plugin;
    private FusionPaper fusion;

    @Override
    public void onEnable() {
        this.fusion = new FusionPaper(this);
        this.fusion.init();

        final Server server = getServer();

        this.plugin = new Pl3xMapPlugin(this.fusion);
        this.plugin.init(server.getConsoleSender());

        final PluginManager pluginManager = server.getPluginManager();

        pluginManager.registerEvents(new CacheListener(), this);
    }

    @Override
    public void onDisable() {
        if (this.plugin == null) return;

        this.plugin.stop();
    }

    public @NotNull final Pl3xMapPlugin getPlugin() {
        return this.plugin;
    }

    public @NotNull final FusionPaper getFusion() {
        return this.fusion;
    }
}
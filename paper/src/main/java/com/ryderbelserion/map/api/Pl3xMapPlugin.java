package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.banners.PaperBannerListener;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Pl3xMapPlugin extends Pl3xMapCommon {

    private final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private final Server server = this.plugin.getServer();

    private final PluginManager pluginManager = this.server.getPluginManager();

    private final FusionPaper fusion;

    public Pl3xMapPlugin(@NotNull final FusionPaper fusion) {
        super(fusion);

        this.fusion = fusion;
    }

    @Override
    public void init() {
        super.init();

        this.pluginManager.registerEvents(new PaperBannerListener(this), this.plugin);
    }

    @Override
    public void reload() {
        super.reload();

        this.fusion.reload();
    }

    @Override
    public void stop() {
        super.stop();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }
}
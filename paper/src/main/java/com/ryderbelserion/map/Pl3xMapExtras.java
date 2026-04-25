package com.ryderbelserion.map;

import com.ryderbelserion.map.api.Pl3xMapPaper;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Pl3xMapExtras extends JavaPlugin {

    public static Pl3xMapExtras getPlugin() {
        return JavaPlugin.getPlugin(Pl3xMapExtras.class);
    }

    private Pl3xMapPaper platform;

    @Override
    public void onEnable() {
        this.platform = new Pl3xMapPaper(this);
        this.platform.init();
    }

    @Override
    public void onDisable() {
        if (this.platform != null) {
            this.platform.shutdown();
        }
    }

    public @NotNull final Pl3xMapPaper getPlatform() {
        return this.platform;
    }
}
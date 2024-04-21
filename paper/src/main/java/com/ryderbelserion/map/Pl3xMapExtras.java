package com.ryderbelserion.map;

import com.ryderbelserion.map.listener.banners.BannerListener;
import com.ryderbelserion.map.listener.banners.BannerWorldListener;
import com.ryderbelserion.map.listener.mobs.MobEntityListener;
import com.ryderbelserion.map.listener.mobs.MobWorldListener;
import com.ryderbelserion.map.markers.banners.BannersLayer;
import com.ryderbelserion.map.markers.mobs.MobsLayer;
import com.ryderbelserion.map.markers.mobs.MobsManager;
import com.ryderbelserion.map.util.FileUtil;
import net.pl3x.map.core.Pl3xMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Pl3xMapExtras extends JavaPlugin {

    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        Server server = getServer();

        if (!server.getPluginManager().isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");

            server.getPluginManager().disablePlugin(this);
        }

        // Register provider
        Provider.register(new Provider.MapExtras(getDataFolder(), getLogger()));

        FileUtil.extracts(getClass(), "/mobs/icons/", getDataFolder().toPath().resolve("mobs").resolve("icons"), false);
        FileUtil.extracts(getClass(), "/banners/icons/", getDataFolder().toPath().resolve("banners").resolve("icons"), false);

        // Enable mob manager
        this.mobsManager = new MobsManager();
        server.getPluginManager().registerEvents(new WorldListener(), this);
        server.getPluginManager().registerEvents(new EntityListener(), this);

        // Enable banners
        pluginManager.registerEvents(new BannerWorldListener(), this);
        pluginManager.registerEvents(new BannerListener(), this);
    }

    @Override
    public void onDisable() {
        Pl3xMap api = Pl3xMap.api();

        api.getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}

            try {
                world.getLayerRegistry().unregister(BannersLayer.KEY);
            } catch (Throwable ignore) {}
        });

        // Unregister provider.
        Provider.unregister();
    }

    public @NotNull MobsManager getMobsManager() {
        return this.mobsManager;
    }
}
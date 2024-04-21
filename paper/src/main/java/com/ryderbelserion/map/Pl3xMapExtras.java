package com.ryderbelserion.map;

import com.ryderbelserion.map.listener.mobs.EntityListener;
import com.ryderbelserion.map.listener.mobs.WorldListener;
import com.ryderbelserion.map.markers.mobs.MobsLayer;
import com.ryderbelserion.map.markers.mobs.MobsManager;
import net.pl3x.map.core.Pl3xMap;
import org.bukkit.Server;
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

        // Enable mob manager
        this.mobsManager = new MobsManager();
        server.getPluginManager().registerEvents(new WorldListener(), this);
        server.getPluginManager().registerEvents(new EntityListener(), this);
    }

    @Override
    public void onDisable() {
        Pl3xMap api = Pl3xMap.api();

        api.getWorldRegistry().forEach(world -> {
            // Unregister mob layer
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}
        });

        // Unregister provider.
        Provider.unregister();
    }

    public @NotNull MobsManager getMobsManager() {
        return this.mobsManager;
    }
}
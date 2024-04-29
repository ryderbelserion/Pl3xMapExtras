package com.ryderbelserion.map;

import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.listener.banners.BannerListener;
import com.ryderbelserion.map.listener.banners.BannerWorldListener;
import com.ryderbelserion.map.listener.claims.ClaimListener;
import com.ryderbelserion.map.listener.mobs.MobEntityListener;
import com.ryderbelserion.map.listener.mobs.MobWorldListener;
import com.ryderbelserion.map.listener.signs.SignListener;
import com.ryderbelserion.map.listener.signs.SignWorldListener;
import com.ryderbelserion.map.listener.warps.WarpListener;
import com.ryderbelserion.map.markers.banners.BannersLayer;
import com.ryderbelserion.map.markers.mobs.MobsLayer;
import com.ryderbelserion.map.markers.mobs.MobsManager;
import com.ryderbelserion.map.markers.signs.SignsLayer;
import com.ryderbelserion.map.util.FileUtil;
import com.ryderbelserion.vital.VitalPaper;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class Pl3xMapExtras extends JavaPlugin {

    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        if (!pluginManager.isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");

            pluginManager.disablePlugin(this);
        }

        new VitalPaper(this);

        // Register provider
        Provider.register(new Provider.MapExtras(getDataFolder(), getLogger()));

        // Extract files
        FileUtil.extracts(getClass(), "/banners/icons/", getDataFolder().toPath().resolve("banners").resolve("icons"), false);
        FileUtil.extracts(getClass(), "/warps/icons/", getDataFolder().toPath().resolve("warps").resolve("icons"), false);
        FileUtil.extracts(getClass(), "/signs/icons/", getDataFolder().toPath().resolve("signs").resolve("icons"), false);
        FileUtil.extracts(getClass(), "/mobs/icons/", getDataFolder().toPath().resolve("mobs").resolve("icons"), false);

        findHooks();

        // Enable mob manager
        this.mobsManager = new MobsManager();

        pluginManager.registerEvents(new MobWorldListener(), this);
        pluginManager.registerEvents(new MobEntityListener(), this);

        // Enable banners
        pluginManager.registerEvents(new BannerWorldListener(), this);
        pluginManager.registerEvents(new BannerListener(), this);

        // Enable signs
        pluginManager.registerEvents(new SignWorldListener(), this);
        pluginManager.registerEvents(new SignListener(), this);

        // Enable warps
        pluginManager.registerEvents(new WarpListener(), this);

        // Enable claims
        pluginManager.registerEvents(new ClaimListener(), this);

        Pl3xMap.api().getWorldRegistry().forEach(Pl3xMapExtras::registerWorld);
    }

    @Override
    public void onDisable() {
        Pl3xMap api = Pl3xMap.api();

        // Unregistry data
        api.getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}

            try {
                world.getLayerRegistry().unregister(BannersLayer.KEY);
            } catch (Throwable ignore) {}

            try {
                world.getLayerRegistry().unregister(SignsLayer.KEY);
            } catch (Throwable ignore) {}

            Pl3xMapExtras.unloadWorld(world);
        });

        // Clear hooks
        Hook.clear();

        // Cancel tasks
        getServer().getScheduler().cancelTasks(this);

        // Unregister provider.
        Provider.unregister();
    }

    public @NotNull MobsManager getMobsManager() {
        return this.mobsManager;
    }

    public void reload() {
        Hook.clear();
        findHooks();
    }

    public void findHooks() {
        Arrays.stream(Hook.Impl.values()).forEach(impl -> {
            if (getServer().getPluginManager().isPluginEnabled(impl.getPluginName())) {
                getLogger().info("Hooking into " + impl.getPluginName());
                Hook.add(impl);
            }
        });
    }

    public static void registerWorld(@NotNull World world) {
        Hook.hooks().forEach(hook -> hook.registerWorld(world));
    }

    public static void unloadWorld(@NotNull World world) {
        Hook.hooks().forEach(hook -> hook.unloadWorld(world));
    }
}
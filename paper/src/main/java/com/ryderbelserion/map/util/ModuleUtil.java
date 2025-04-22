package com.ryderbelserion.map.util;

import com.ryderbelserion.fusion.core.utils.FileUtils;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.hook.claims.claimchunk.ClaimChunkConfig;
import com.ryderbelserion.map.hook.claims.griefdefender.GriefDefenderConfig;
import com.ryderbelserion.map.hook.claims.griefprevention.GriefPreventionConfig;
import com.ryderbelserion.map.hook.claims.plotsquared.P2Config;
import com.ryderbelserion.map.hook.claims.worldguard.WorldGuardConfig;
import com.ryderbelserion.map.hook.warps.playerwarps.PlayerWarpsLayer;
import com.ryderbelserion.map.listener.banners.BannerListener;
import com.ryderbelserion.map.listener.banners.BannerWorldListener;
import com.ryderbelserion.map.listener.claims.ClaimListener;
import com.ryderbelserion.map.listener.mobs.MobEntityListener;
import com.ryderbelserion.map.listener.mobs.MobWorldListener;
import com.ryderbelserion.map.listener.signs.SignListener;
import com.ryderbelserion.map.listener.signs.SignWorldListener;
import com.ryderbelserion.map.listener.warps.WarpListener;
import com.ryderbelserion.map.marker.banners.BannersLayer;
import com.ryderbelserion.map.marker.mobs.MobsLayer;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.marker.signs.SignsLayer;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Arrays;

public class ModuleUtil {

    private static final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private static final Server server = plugin.getServer();

    private static final PluginManager pluginManager = server.getPluginManager();

    public static void toggleAll(boolean isShutdown) {
        toggleSigns(isShutdown);
        toggleWarps(isShutdown);
        toggleBanners(isShutdown);

        toggleMobs(isShutdown);
        toggleClaims(isShutdown);
    }

    public static void toggleClaims(final boolean isShutdown) {
        if (ConfigUtil.isClaimsEnabled() && !isShutdown) {
            pluginManager.registerEvents(new ClaimListener(), plugin);

            Pl3xMap.api().getWorldRegistry().forEach(ModuleUtil::registerWorld);

            if (pluginManager.isPluginEnabled("WorldGuard")) {
                WorldGuardConfig.reload();
            }

            if (pluginManager.isPluginEnabled("GriefPrevention")) {
                GriefPreventionConfig.reload();
            }

            if (pluginManager.isPluginEnabled("PlotSquared")) {
                P2Config.reload();
            }

            if (pluginManager.isPluginEnabled("GriefDefender")) {
                GriefDefenderConfig.reload();
            }

            if (pluginManager.isPluginEnabled("ClaimChunk")) {
                ClaimChunkConfig.reload();
            }

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(ModuleUtil::unloadWorld);

        if (!isShutdown) {
            server.getGlobalRegionScheduler().cancelTasks(plugin);
        }
    }

    public static void toggleWarps(final boolean isShutdown) {
        if (ConfigUtil.isWarpsEnabled() && !isShutdown) {
            pluginManager.registerEvents(new WarpListener(), plugin);

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(PlayerWarpsLayer.KEY);
            } catch (Throwable ignore) {}
        });
    }

    public static void toggleSigns(final boolean isShutdown) {
        if (ConfigUtil.isSignsEnabled() && !isShutdown) {
            pluginManager.registerEvents(new SignWorldListener(), plugin);
            pluginManager.registerEvents(new SignListener(), plugin);

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(SignsLayer.KEY);
            } catch (Throwable ignore) {}
        });
    }

    public static void toggleBanners(final boolean isShutdown) {
        if (ConfigUtil.isBannersEnabled() && !isShutdown) {
            pluginManager.registerEvents(new BannerWorldListener(), plugin);
            pluginManager.registerEvents(new BannerListener(), plugin);

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(BannersLayer.KEY);
            } catch (Throwable ignore) {}
        });
    }

    public static void toggleMobs(final boolean isShutdown) {
        if (ConfigUtil.isMobsEnabled() && !isShutdown) {
            pluginManager.registerEvents(new MobWorldListener(), plugin);
            pluginManager.registerEvents(new MobEntityListener(), plugin);

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}
        });

        MobsManager manager = plugin.getMobsManager();

        if (manager != null) {
            manager.clearAll();
        }

        if (!isShutdown) {
            server.getGlobalRegionScheduler().cancelTasks(plugin);
        }
    }

    public static void registerWorld(@NotNull World world) {
        Hook.hooks().forEach(hook -> hook.registerWorld(world));
    }

    public static void unloadWorld(@NotNull World world) {
        Hook.hooks().forEach(hook -> hook.unloadWorld(world));
    }

    public static void findHooks() {
        Arrays.stream(Hook.Impl.values()).forEach(impl -> {
            if (pluginManager.isPluginEnabled(impl.getPluginName())) {
                plugin.getLogger().info("Hooking into " + impl.getPluginName());
                Hook.add(impl);
            }
        });
    }

    public static void unload() {
        // Unregister data.
        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}

            try {
                world.getLayerRegistry().unregister(BannersLayer.KEY);
            } catch (Throwable ignore) {}

            try {
                world.getLayerRegistry().unregister(SignsLayer.KEY);
            } catch (Throwable ignore) {}

            ModuleUtil.unloadWorld(world);
        });
    }

    public static void reload() {
        Hook.clear();
        findHooks();
    }

    public static void extract() {
        final Path path = plugin.getDataFolder().toPath();

        if (ConfigUtil.isBannersEnabled()) {
            FileUtils.extract("banners/icons", path, true, false);
        }

        if (ConfigUtil.isWarpsEnabled()) {
            FileUtils.extract("warps/icons", path, true, false);
        }

        if (ConfigUtil.isSignsEnabled()) {
            FileUtils.extract("signs/icons", path, true, false);
        }

        if (ConfigUtil.isMobsEnabled()) {
            FileUtils.extract("mobs/icons", path, true, false);
        }
    }
}
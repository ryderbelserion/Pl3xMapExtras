package com.ryderbelserion.map.util;

import com.ryderbelserion.fusion.core.api.enums.Level;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.common.configs.ConfigManager;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.hook.claims.griefdefender.GriefDefenderConfig;
import com.ryderbelserion.map.hook.claims.griefprevention.GriefPreventionConfig;
import com.ryderbelserion.map.hook.claims.plotsquared.P2Config;
import com.ryderbelserion.map.hook.claims.worldguard.WorldGuardConfig;
import com.ryderbelserion.map.hook.warps.playerwarps.PlayerWarpsLayer;
import com.ryderbelserion.map.listener.claims.ClaimListener;
import com.ryderbelserion.map.listener.mobs.MobEntityListener;
import com.ryderbelserion.map.listener.mobs.MobWorldListener;
import com.ryderbelserion.map.listener.signs.SignListener;
import com.ryderbelserion.map.listener.signs.SignWorldListener;
import com.ryderbelserion.map.listener.warps.WarpListener;
import com.ryderbelserion.map.marker.mobs.MobsLayer;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.marker.signs.SignsLayer;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class ModuleUtil {

    private static final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private static final Pl3xMapPaper platform = plugin.getPlatform();

    private static final FusionPaper fusion = platform.getFusion();

    private static final ConfigManager configManager = platform.getConfigManager();

    private static final Server server = plugin.getServer();

    private static final PluginManager pluginManager = server.getPluginManager();

    public static void toggleAll(final boolean isShutdown) {
        toggleSigns(isShutdown);
        toggleWarps(isShutdown);

        toggleMobs(isShutdown);
        toggleClaims(isShutdown);
    }

    public static void toggleClaims(final boolean isShutdown) {
        final BasicConfig config = configManager.getConfig();

        if (config.isClaimsEnabled() && !isShutdown) {
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

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(ModuleUtil::unloadWorld);

        if (!isShutdown) {
            server.getGlobalRegionScheduler().cancelTasks(plugin);
        }
    }

    public static void toggleWarps(final boolean isShutdown) {
        final BasicConfig config = configManager.getConfig();

        if (config.isWarpsEnabled() && !isShutdown) {
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
        final BasicConfig config = configManager.getConfig();

        if (config.isSignsEnabled() && !isShutdown) {
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

    public static void toggleMobs(final boolean isShutdown) {
        final BasicConfig config = configManager.getConfig();

        if (config.isMobsEnabled() && !isShutdown) {
            pluginManager.registerEvents(new MobWorldListener(), plugin);
            pluginManager.registerEvents(new MobEntityListener(), plugin);

            return;
        }

        Pl3xMap.api().getWorldRegistry().forEach(world -> {
            try {
                world.getLayerRegistry().unregister(MobsLayer.KEY);
            } catch (Throwable ignore) {}
        });


        platform.getMobsManager().ifPresent(MobsManager::clearAll);

        if (!isShutdown) {
            server.getGlobalRegionScheduler().cancelTasks(plugin);
        }
    }

    public static void registerWorld(@NotNull final World world) {
        Hook.hooks().forEach(hook -> hook.registerWorld(world));
    }

    public static void unloadWorld(@NotNull final World world) {
        Hook.hooks().forEach(hook -> hook.unloadWorld(world));
    }

    public static void findHooks() {
        Arrays.stream(Hook.Impl.values()).forEach(impl -> {
            if (pluginManager.isPluginEnabled(impl.getPluginName())) {
                fusion.log(Level.INFO, "<red>Hooking into <yellow>%s".formatted(impl.getPluginName()));

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
                world.getLayerRegistry().unregister(SignsLayer.KEY);
            } catch (Throwable ignore) {}

            ModuleUtil.unloadWorld(world);
        });
    }

    public static void reload() {
        Hook.clear();
        findHooks();
    }
}
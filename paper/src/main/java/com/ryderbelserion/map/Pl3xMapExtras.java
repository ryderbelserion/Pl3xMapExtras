package com.ryderbelserion.map;

import com.ryderbelserion.map.api.MetricsWrapper;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.config.PluginConfig;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.util.ConfigUtil;
import com.ryderbelserion.map.util.ModuleUtil;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class Pl3xMapExtras extends JavaPlugin {

    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        final PluginManager pluginManager = getServer().getPluginManager();

        if (!pluginManager.isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");

            pluginManager.disablePlugin(this);

            return;
        }

        // Register the provider.
        Provider.register(new Provider.MapExtras(getDataFolder(), getLogger()));

        // Load the config.
        PluginConfig.reload();

        // Extract the files needed for the plugin.
        ModuleUtil.extract();

        // Find all plugin hooks and load them.
        ModuleUtil.findHooks();

        // Create mob manager class.
        this.mobsManager = new MobsManager();

        // Toggle all our shit on startup.
        ModuleUtil.toggleAll(false);

        // Register the permissions.
        Arrays.stream(Permissions.values()).toList().forEach(permission -> {
            Permission newPermission = new Permission(
                    permission.getPermission(),
                    permission.getDescription(),
                    permission.isDefault()
            );

            getServer().getPluginManager().addPermission(newPermission);
        });

        // Register the commands.
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final @NotNull Commands registry = event.registrar();

            registry.register("pl3xmapextras", "the command to handle the plugin", new BaseCommand());
        });

        new MetricsWrapper(this, 22296).start();
    }

    @Override
    public void onDisable() {
        // Cancel the tasks regardless
        getServer().getGlobalRegionScheduler().cancelTasks(this);
        getServer().getAsyncScheduler().cancelTasks(this);

        // Unregister data.
        ModuleUtil.unload();

        // Clean up all our shit on shutdown.
        ModuleUtil.toggleAll(true);

        // Clear plugin hooks.
        Hook.clear();

        // Unregister provider.
        Provider.unregister();
    }

    public MobsManager getMobsManager() {
        if (!ConfigUtil.isMobsEnabled()) {
            getLogger().warning("The toggle for displaying a mob layer is turned off.");

            return null;
        }

        return this.mobsManager;
    }
}
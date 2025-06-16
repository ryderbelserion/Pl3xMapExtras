package com.ryderbelserion.map;

import com.ryderbelserion.fusion.paper.FusionPaper;
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
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;

public class Pl3xMapExtras extends JavaPlugin {

    private MobsManager mobsManager;

    private FusionPaper api;

    @Override
    public void onEnable() {
        this.api = new FusionPaper(getLogger(), getDataPath());
        this.api.enable(this);

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
            @NotNull final Commands registry = event.registrar();

            registry.register("pl3xmapextras", "the command to handle the plugin", new BaseCommand());
        });

        new MetricsWrapper(this, 22296);
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

        if (this.api != null) {
            this.api.disable();
        }
    }

    public @Nullable final MobsManager getMobsManager() {
        if (!ConfigUtil.isMobsEnabled()) {
            getLogger().warning("The toggle for displaying a mob layer is turned off.");

            return null;
        }

        return this.mobsManager;
    }

    public final FusionPaper getFusion() {
        return this.api;
    }
}
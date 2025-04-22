package com.ryderbelserion.map;

import ch.jalu.configme.SettingsManager;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.api.MetricsWrapper;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.config.ConfigManager;
import com.ryderbelserion.map.config.types.ConfigKeys;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.util.ModuleUtil;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

public class Pl3xMapExtras extends JavaPlugin {

    private MobsManager mobsManager;
    private ConfigManager configManager;
    private FusionPaper api;

    @Override
    public void onEnable() {
        final ComponentLogger logger = getComponentLogger();
        final Path path = getDataPath();

        this.api = new FusionPaper(logger, path);
        this.api.enable(this);

        final Pl3xMapExtrasCore core = new Pl3xMapExtrasCore(this.api.getFileManager(), logger, path);

        this.configManager = core.getConfigManager();

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

    public @NotNull final Optional<MobsManager> getMobsManager() {
        final SettingsManager config = getConfigManager().getConfig().getConfig();

        if (!config.getProperty(ConfigKeys.toggle_mobs)) {
            return Optional.empty();
        }

        return Optional.ofNullable(this.mobsManager);
    }

    public @NotNull final ConfigManager getConfigManager() {
        return this.configManager;
    }

    public @NotNull final FusionPaper getFusion() {
        return this.api;
    }
}
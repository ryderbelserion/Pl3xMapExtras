package com.ryderbelserion.map;

import com.ryderbelserion.map.config.PluginConfig;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.markers.mobs.MobsManager;
import com.ryderbelserion.map.util.ModuleUtil;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Pl3xMapExtras extends JavaPlugin {

    private final @NotNull BukkitCommandManager<CommandSender> commandManager = BukkitCommandManager.create(this);

    private MobsManager mobsManager;

    @Override
    public void onEnable() {
        PluginManager pluginManager = getServer().getPluginManager();

        if (!pluginManager.isPluginEnabled("Pl3xMap")) {
            getLogger().severe("Pl3xMap not found!");

            pluginManager.disablePlugin(this);
        }

        // Register the provider.
        Provider.register(new Provider.MapExtras(getDataFolder(), getLogger()));

        // Extract the files needed for the plugin.
        ModuleUtil.extract();

        // Find all plugin hooks and load them.
        ModuleUtil.findHooks();

        // Create mob manager class.
        this.mobsManager = new MobsManager();

        // Toggle all our shit on startup.
        ModuleUtil.toggleAll(false);

        // Register the commands.
        this.commandManager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> sender.sendRichMessage(PluginConfig.no_permission.replace("{prefix}", PluginConfig.msg_prefix)));
        this.commandManager.registerCommand(new BaseCommand());
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
        if (!ModuleUtil.isMobsEnabled()) {
            getLogger().warning("The toggle for displaying a mob layer is turned off.");

            return null;
        }

        return this.mobsManager;
    }
}
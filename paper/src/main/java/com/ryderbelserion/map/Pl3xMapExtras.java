package com.ryderbelserion.map;

import com.ryderbelserion.map.api.MetricsWrapper;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.api.enums.Permissions;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Arrays;

public class Pl3xMapExtras extends JavaPlugin {

    public static Pl3xMapExtras getPlugin() {
        return JavaPlugin.getPlugin(Pl3xMapExtras.class);
    }

    private Pl3xMapPaper platform;

    @Override
    public void onEnable() {
        this.platform = new Pl3xMapPaper(getFile(), this);
        this.platform.init();

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
        if (this.platform != null) {
            this.platform.shutdown();
        }
    }

    public @NotNull final Pl3xMapPaper getPlatform() {
        return this.platform;
    }
}
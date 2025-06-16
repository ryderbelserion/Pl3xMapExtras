package com.ryderbelserion.map.api.enums;

import com.ryderbelserion.map.Pl3xMapExtras;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public enum Permissions {

    banners_admin("banners.admin", "allows the user to have banners show up on the map", PermissionDefault.OP),
    signs_admin("signs.admin", "allows the user to have signs show up on the map", PermissionDefault.OP),

    reload("reload", "the reload command", PermissionDefault.OP),
    help("help", "help command", PermissionDefault.OP);

    private final PermissionDefault isDefault;
    private final String description;
    private final String node;

    Permissions(@NotNull final String node, @NotNull final String description, @NotNull final PermissionDefault isDefault) {
        this.description = description;
        this.isDefault = isDefault;
        this.node = node;
    }

    public final String getPermission() {
        return JavaPlugin.getProvidingPlugin(Pl3xMapExtras.class).getPluginMeta().getName() + this.node;
    }

    public final boolean hasPermission(@NotNull final CommandSender player) {
        return player.hasPermission(getPermission()) && !(player instanceof ConsoleCommandSender);
    }

    public final PermissionDefault isDefault() {
        return this.isDefault;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getNode() {
        return this.node;
    }
}
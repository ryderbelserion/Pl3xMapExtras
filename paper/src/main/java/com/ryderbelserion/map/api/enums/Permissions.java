package com.ryderbelserion.map.api.enums;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

public enum Permissions {

    signs_admin("signs.admin", "allows the user to add signs to the web map via block interaction.", PermissionDefault.OP),

    banners_admin("banners.admin", "allows the user to add banners to the web map via block interaction.", PermissionDefault.OP),
    banners_place("banners.place", "allows the user to add banners to the web map via block placement.", PermissionDefault.OP),
    banners_remove("banners.remove", "allows the user to remove banners from the web map.", PermissionDefault.OP);

    private final PermissionDefault isDefault;
    private final String description;
    private final String node;

    Permissions(@NotNull final String node, @NotNull final String description, @NotNull final PermissionDefault isDefault) {
        this.description = description;
        this.isDefault = isDefault;
        this.node = node;
    }

    public final String getPermission() {
        return "pl3xmapextras" + this.node;
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
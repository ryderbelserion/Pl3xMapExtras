package com.ryderbelserion.map;

import com.ryderbelserion.map.config.PluginConfig;
import com.ryderbelserion.map.util.ModuleUtil;
import dev.triumphteam.cmd.bukkit.annotation.Permission;
import dev.triumphteam.cmd.core.annotations.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.PermissionDefault;

@Command(value = "pl3xmapextras")
public class BaseCommand {

    @Command
    @Permission(value = "pl3xmapextras.reload", def = PermissionDefault.OP)
    public void reload(CommandSender sender) {
        // Reload the plugin.
        PluginConfig.reload();

        // Turn on or off all our shit
        ModuleUtil.toggleAll(false);

        // Extract all our shit if it's missing.
        ModuleUtil.extract();

        // Send message.
        sender.sendRichMessage(PluginConfig.reload_plugin.replace("{prefix}", PluginConfig.msg_prefix));
    }
}
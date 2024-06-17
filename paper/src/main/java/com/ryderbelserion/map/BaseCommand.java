package com.ryderbelserion.map;

import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.config.PluginConfig;
import com.ryderbelserion.map.util.ModuleUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class BaseCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String[] args) {
        final CommandSender sender = stack.getSender();

        switch (args.length) {
            case 0 -> help(sender);

            case 1 -> {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!Permissions.reload.hasPermission(sender) && !(sender instanceof ConsoleCommandSender)) {
                        sender.sendRichMessage(PluginConfig.no_permission.replace("{prefix}", PluginConfig.msg_prefix));

                        return;
                    }

                    PluginConfig.reload();

                    ModuleUtil.toggleAll(false);

                    ModuleUtil.extract();

                    sender.sendRichMessage(PluginConfig.reload_plugin.replace("{prefix}", PluginConfig.msg_prefix));

                    return;
                }

                help(sender);
            }
        }
    }

    private void help(final CommandSender sender) {
        if (!Permissions.help.hasPermission(sender)) {
            sender.sendRichMessage(PluginConfig.no_permission.replace("{prefix}", PluginConfig.msg_prefix));

            return;
        }

        sender.sendRichMessage(PluginConfig.help_message.replace("{prefix}", PluginConfig.msg_prefix));
    }
}
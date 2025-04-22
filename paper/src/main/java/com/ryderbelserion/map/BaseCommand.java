package com.ryderbelserion.map;

import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.config.types.ConfigKeys;
import com.ryderbelserion.map.util.ModuleUtil;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseCommand implements BasicCommand {

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        final CommandSender sender = stack.getSender();

        switch (args.length) {
            case 0 -> help(sender);

            case 1 -> {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!Permissions.reload.hasPermission(sender) && !(sender instanceof ConsoleCommandSender)) {
                        sender.sendRichMessage(ConfigKeys.no_permission.replace("{prefix}", ConfigKeys.msg_prefix));

                        return;
                    }

                    ConfigKeys.reload();

                    ModuleUtil.toggleAll(false);

                    ModuleUtil.extract();

                    sender.sendRichMessage(ConfigKeys.reload_plugin.replace("{prefix}", ConfigKeys.msg_prefix));

                    return;
                }

                help(sender);
            }
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        final Collection<String> suggestions = new ArrayList<>();

        if (args.length == 0) {
            if (Permissions.reload.hasPermission(stack.getSender())) suggestions.add("reload");
            if (Permissions.help.hasPermission(stack.getSender())) suggestions.add("help");
        }

        return suggestions;
    }

    private void help(@NotNull final CommandSender sender) {
        if (!Permissions.help.hasPermission(sender)) {
            sender.sendRichMessage(ConfigKeys.no_permission.replace("{prefix}", ConfigKeys.msg_prefix));

            return;
        }

        sender.sendRichMessage(convertList(ConfigKeys.help_message).replace("{prefix}", ConfigKeys.msg_prefix));
    }

    private String convertList(@NotNull final List<String> list) {
        if (list.isEmpty()) return "";

        StringBuilder message = new StringBuilder();

        for (String line : list) {
            message.append(line).append("\n");
        }

        return StringUtils.chomp(message.toString());
    }
}
package com.ryderbelserion.map;

import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.api.constants.Messages;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.api.registry.adapters.PaperSenderAdapter;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.Collection;

public class BaseCommand implements BasicCommand {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Pl3xMapPaper platform = this.plugin.getPlatform();

    private final PaperSenderAdapter adapter = this.platform.getSenderAdapter();

    @Override
    public void execute(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        final CommandSender sender = stack.getSender();

        switch (args.length) {
            case 0 -> help(sender);

            case 1 -> {
                if (args[0].equalsIgnoreCase("reload")) {
                    if (!Permissions.reload.hasPermission(sender) && !(sender instanceof ConsoleCommandSender)) {
                        this.adapter.sendMessage(sender, Messages.no_permission);

                        return;
                    }

                    this.platform.reload();

                    this.adapter.sendMessage(sender, Messages.reload_plugin);

                    return;
                }

                help(sender);
            }
        }
    }

    @Override
    public @NotNull Collection<String> suggest(@NotNull CommandSourceStack stack, @NotNull String @NotNull [] args) {
        final Collection<String> suggestions = new ArrayList<>();

        final CommandSender sender = stack.getSender();

        if (args.length == 0) {
            if (Permissions.reload.hasPermission(sender)) suggestions.add("reload");
            if (Permissions.help.hasPermission(sender)) suggestions.add("help");
        }

        return suggestions;
    }

    private void help(@NotNull final CommandSender sender) {
        if (!Permissions.help.hasPermission(sender)) {
            this.adapter.sendMessage(sender, Messages.no_permission);

            return;
        }

        this.adapter.sendMessage(sender, Messages.help);
    }
}
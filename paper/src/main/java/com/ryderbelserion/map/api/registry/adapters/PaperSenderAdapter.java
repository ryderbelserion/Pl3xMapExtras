package com.ryderbelserion.map.api.registry.adapters;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.api.registry.PaperMessageRegistry;
import com.ryderbelserion.map.api.registry.PaperUserRegistry;
import com.ryderbelserion.map.common.api.adapters.sender.ISenderAdapter;
import com.ryderbelserion.map.common.configs.ConfigManager;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PaperSenderAdapter extends ISenderAdapter<Pl3xMapPaper, Component, CommandSender> {

    private final PaperMessageRegistry messageRegistry;
    private final PaperUserRegistry userRegistry;
    private final ConfigManager configManager;
    private final FusionPaper fusion;

    public PaperSenderAdapter(@NotNull final Pl3xMapPaper platform) {
        this.messageRegistry = platform.getMessageRegistry();
        this.userRegistry = platform.getUserRegistry();
        this.configManager = platform.getConfigManager();

        this.fusion = platform.getFusion();
    }

    @Override
    public UUID getUniqueId(@NotNull final CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getUniqueId();
        }

        return Pl3xMapPlugin.CONSOLE_UUID;
    }

    @Override
    public String getName(@NotNull final CommandSender sender) {
        if (sender instanceof Player player) {
            return player.getName();
        }

        return Pl3xMapPlugin.CONSOLE_NAME;
    }

    @Override
    public void sendMessage(@NotNull final CommandSender sender, @NotNull final Key id, @NotNull final Map<String, String> placeholders) {
        sender.sendMessage(getComponent(sender, id, placeholders));
    }

    @Override
    public Component getComponent(@NotNull final CommandSender sender, @NotNull final Key id, @NotNull final Map<String, String> placeholders) {
        final Map<String, String> map = new HashMap<>(placeholders);

        final BasicConfig configuration = this.configManager.getConfig();

        final String prefix = configuration.getPrefix();

        if (!prefix.isEmpty()) {
            map.putIfAbsent("{prefix}", prefix);
        }

        if (!(sender instanceof Player player)) {
            return this.fusion.parse(sender, this.messageRegistry.getMessage(id).getValue(), map);
        }

        final Optional<PaperUserAdapter> optional = this.userRegistry.getUser(player.getUniqueId());

        if (optional.isEmpty()) return this.fusion.parse(player, this.messageRegistry.getMessage(id).getValue(), map);

        final PaperUserAdapter user = optional.get();

        return this.fusion.parse(player, this.messageRegistry.getMessageByLocale(user.getLocaleKey(), id).getValue(), map);
    }

    @Override
    public boolean isConsole(@NotNull final CommandSender sender) {
        return sender instanceof ConsoleCommandSender;
    }
}
package com.ryderbelserion.map.api.commands;

import com.ryderbelserion.map.commands.player.ISource;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class PaperSource extends ISource {

    public PaperSource(@NotNull final Audience audience) {
        super(audience);
    }

    @Override
    public final boolean hasPermission(@NotNull final String value) {
        return getAudience().hasPermission(value);
    }

    @Override
    public @NotNull final CommandSender getAudience() {
        return (CommandSender) super.getAudience();
    }
}
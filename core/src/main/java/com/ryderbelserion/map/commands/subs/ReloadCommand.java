package com.ryderbelserion.map.commands.subs;

import com.mojang.brigadier.context.CommandContext;
import com.ryderbelserion.map.commands.BaseCommand;
import com.ryderbelserion.map.commands.player.ISource;
import com.ryderbelserion.map.enums.constants.Messages;
import com.ryderbelserion.map.enums.Mode;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

public class ReloadCommand<S> extends BaseCommand<S> {

    public ReloadCommand(@NotNull final Function<S, ISource> function) {
        super("Gives access to reloading the plugin", "pl3xmapextras.reload", "reload", Mode.OP, function);
    }

    @Override
    protected int execute(@NotNull CommandContext<S> context) {
        final ISource source = getSource(context);

        this.plugin.reload();

        this.userRegistry.getUser(source.getAudience()).sendMessage(Messages.reload_plugin);

        return 1;
    }
}
package com.ryderbelserion.map.commands.subs;

import com.mojang.brigadier.context.CommandContext;
import com.ryderbelserion.map.commands.BaseCommand;
import com.ryderbelserion.map.commands.player.ISource;
import com.ryderbelserion.map.enums.Mode;
import org.jetbrains.annotations.NotNull;
import java.util.function.Function;

public class CoreCommand<S> extends BaseCommand<S> {

    public CoreCommand(@NotNull final String permission,
                       @NotNull final String literal,
                       @NotNull final Function<S, ISource> function) {
        super("The base command for the plugin!", permission, literal, Mode.OP, function);

        then(new ReloadCommand<>(function));
    }

    @Override
    protected int execute(@NotNull CommandContext<S> context) {
        return 1;
    }
}
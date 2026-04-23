package com.ryderbelserion.map.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.common.api.adapters.sender.ISenderAdapter;
import com.ryderbelserion.map.common.commands.player.ISource;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public abstract class BaseCommand<S> extends LiteralArgumentBuilder<S> {

    protected final Pl3xMapPlugin plugin = (Pl3xMapPlugin) Pl3xMapExtras.Provider.getInstance();

    protected final ISenderAdapter adapter = this.plugin.getSenderAdapter();

    private final Function<S, ISource> function;
    private final String permission;

    public BaseCommand(@NotNull final String description, @NotNull final String parent, @NotNull final Map<String, Boolean> children, @NotNull final String literal, @NotNull final Mode mode, @NotNull final Function<S, ISource> function) {
        super(literal);

        this.plugin.registerPermission(mode, this.permission = parent, description, children);

        this.function = function;

        requires(stack -> function.apply(stack).hasPermission(this.permission));

        executes(this::execute);
    }

    public BaseCommand(@NotNull final String description, @NotNull final String parent, @NotNull final String literal, @NotNull final Mode mode, @NotNull final Function<S, ISource> function) {
        this(description, parent, new HashMap<>(), literal, mode, function);
    }

    protected @NotNull final ISource getSource(@NotNull final CommandContext<S> context) {
        return this.function.apply(context.getSource());
    }

    protected abstract int execute(@NotNull final CommandContext<S> context);

    public @NotNull final String getPermission() {
        return this.permission;
    }
}
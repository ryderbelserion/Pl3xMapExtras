package com.ryderbelserion.map.common.api.adapters;

import com.ryderbelserion.map.api.adapters.IPlayerAdapter;
import com.ryderbelserion.map.api.registry.IContextRegistry;
import com.ryderbelserion.map.api.registry.IUserRegistry;
import com.ryderbelserion.map.api.user.IUser;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class PlayerAdapter<P> implements IPlayerAdapter<P> {

    private final IUserRegistry<?> userRegistry;
    private final IContextRegistry<P> contextRegistry;

    public PlayerAdapter(@NotNull final IUserRegistry<?> userRegistry, @NotNull final IContextRegistry<P> contextRegistry) {
        this.userRegistry = userRegistry;
        this.contextRegistry = contextRegistry;
    }

    @Override
    public @NotNull final Optional<? extends IUser> getUser(@NotNull final P player) {
        return this.userRegistry.getUser(this.contextRegistry.getUUID(player));
    }
}
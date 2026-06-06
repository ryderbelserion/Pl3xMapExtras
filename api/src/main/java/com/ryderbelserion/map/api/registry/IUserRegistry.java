package com.ryderbelserion.map.api.registry;

import com.ryderbelserion.map.api.user.IUser;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface IUserRegistry<S> {

    Optional<? extends IUser> getUser(@NotNull final UUID uuid);

    void updateCache(@NotNull final UUID uuid, @NotNull final String name);

    boolean isCached(@NotNull final UUID uuid);

    String getCache(@NotNull final UUID uuid);

    IUser removeUser(@NotNull final UUID uuid);

    IUser addUser(@NotNull final S player);

    IUser getConsole();

    boolean hasUser(@NotNull final UUID uuid);

    void init();

}
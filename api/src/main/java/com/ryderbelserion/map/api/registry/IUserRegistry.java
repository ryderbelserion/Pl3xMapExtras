package com.ryderbelserion.map.api.registry;

import com.ryderbelserion.map.api.user.IUser;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.UUID;

public interface IUserRegistry<S> {

    Optional<? extends IUser> getUser(@NotNull final UUID uuid);

    IUser removeUser(@NotNull final UUID uuid);

    IUser addUser(@NotNull final S player);

    IUser getConsole();

    void init();

}
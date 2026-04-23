package com.ryderbelserion.map.api.user;

import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public abstract class IUser {

    public abstract @NotNull String getUsername();

    public abstract @NotNull UUID getUniqueId();

    public abstract @NotNull Key getLocaleKey();

    @ApiStatus.Internal
    public abstract void setLocale(@NotNull final String locale);

    public @NotNull String getLocale() {
        return getLocaleKey().asString();
    }
}
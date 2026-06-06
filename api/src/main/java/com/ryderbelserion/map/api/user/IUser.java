package com.ryderbelserion.map.api.user;

import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.storage.IStorageHolder;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public abstract class IUser {

    protected final Pl3xMapExtras pl3x = Pl3xMapExtras.Provider.getInstance();

    protected final IStorageHolder storage = this.pl3x.getStorageHolder();

    public abstract @NotNull String getUsername();

    public abstract @NotNull UUID getUniqueId();

    public abstract @NotNull Key getLocaleKey();

    public void init() {
        if (this.storage.hasUser(this.getUniqueId())) {
            return;
        }

        this.storage.insertUser(this);
    }

    @ApiStatus.Internal
    public abstract void setLocale(@NotNull final String locale);

    public @NotNull String getLocale() {
        return getLocaleKey().asString();
    }
}
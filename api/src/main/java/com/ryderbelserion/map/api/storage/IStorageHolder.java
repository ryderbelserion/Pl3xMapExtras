package com.ryderbelserion.map.api.storage;

import com.ryderbelserion.map.api.user.IUser;
import org.jspecify.annotations.NonNull;
import java.util.UUID;

public abstract class IStorageHolder {

    public abstract @NonNull IStorageHolder init();

    public abstract void reload();

    public abstract void insertUser(@NonNull final IUser user);

    public abstract String getName(@NonNull final UUID uuid);

    public boolean hasUser(@NonNull final UUID uuid) { // only used for sql storage.
        return false;
    }

    public abstract void stop();

}
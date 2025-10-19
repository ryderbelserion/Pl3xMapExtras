package com.ryderbelserion.map.registry;

import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapProvider;
import com.ryderbelserion.map.objects.users.User;
import com.ryderbelserion.map.objects.users.interfaces.IUserRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.identity.Identity;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class UserRegistry implements IUserRegistry<User> {

    private final Pl3xMapCommon plugin = Pl3xMapProvider.getInstance();

    private final Map<UUID, User> users = new HashMap<>();

    public void init(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            this.users.put(Pl3xMapCommon.console, new User(audience));
        }
    }

    @Override
    public void addUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return;
        }

        final Optional<UUID> uuid = audience.get(Identity.UUID);

        uuid.ifPresent(value -> {
            final User user = new User(audience);

            final Optional<Locale> locale = audience.get(Identity.LOCALE);

            locale.ifPresent(user::setLocale);

            this.users.put(value, user);
        });
    }

    @Override
    public void removeUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return;
        }

        final Optional<UUID> uuid = audience.get(Identity.UUID);

        uuid.ifPresent(this.users::remove);
    }

    @Override
    public final boolean hasUser(@NotNull final UUID uuid) {
        return this.users.containsKey(uuid);
    }

    @Override
    public @NotNull final User getUser(@NotNull final Audience audience) {
        if (this.plugin.isConsoleSender(audience)) {
            return this.users.get(Pl3xMapCommon.console);
        }

        final Optional<UUID> optional = audience.get(Identity.UUID);

        //noinspection OptionalGetWithoutIsPresent
        return this.users.get(optional.get());
    }

    @Override
    public @NotNull final Map<UUID, User> getUsers() {
        return this.users;
    }
}
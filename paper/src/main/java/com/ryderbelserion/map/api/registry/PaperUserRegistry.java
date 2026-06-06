package com.ryderbelserion.map.api.registry;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.registry.adapters.PaperUserAdapter;
import com.ryderbelserion.map.api.user.IUser;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class PaperUserRegistry implements IUserRegistry<Player> {

    private final Map<UUID, PaperUserAdapter> users = new HashMap<>();

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Cache<UUID, String> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    @Override
    public void init() {
        this.users.put(Pl3xMapPlugin.CONSOLE_UUID, new PaperUserAdapter());
    }

    @Override
    public PaperUserAdapter addUser(@NotNull final Player player) {
        final PaperUserAdapter adapter = new PaperUserAdapter(player);

        adapter.init();

        final UUID uuid = player.getUniqueId();

        if (isCached(uuid)) {
            this.cache.invalidate(uuid);
        }

        return this.users.putIfAbsent(uuid, adapter);
    }

    @Override
    public PaperUserAdapter removeUser(@NotNull final UUID uuid) {
        return this.users.remove(uuid);
    }

    @Override
    public Optional<PaperUserAdapter> getUser(@NotNull final UUID uuid) {
        final Server server = this.plugin.getServer();

        final Player player = server.getPlayer(uuid);

        if (!hasUser(uuid) && player != null) {
            return Optional.ofNullable(addUser(player));
        }

        return Optional.ofNullable(this.users.get(uuid));
    }

    @Override
    public void updateCache(@NotNull final UUID uuid, @NotNull final String name) {
        this.cache.put(uuid, name);
    }

    @Override
    public final boolean isCached(@NotNull final UUID uuid) {
        return this.cache.getIfPresent(uuid) != null;
    }

    @Override
    public String getCache(@NotNull final UUID uuid) {
        return this.cache.getIfPresent(uuid);
    }

    @Override
    public @NotNull final IUser getConsole() {
        return this.users.get(Pl3xMapPlugin.CONSOLE_UUID);
    }

    @Override
    public final boolean hasUser(@NotNull final UUID uuid) {
        return this.users.containsKey(uuid);
    }
}
package com.ryderbelserion.map.api.registry;

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

public class PaperUserRegistry implements IUserRegistry<Player> {

    private final Map<UUID, PaperUserAdapter> users = new HashMap<>();

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    @Override
    public void init() {
        this.users.put(Pl3xMapPlugin.CONSOLE_UUID, new PaperUserAdapter());
    }

    @Override
    public PaperUserAdapter addUser(@NotNull final Player player) {
        final PaperUserAdapter adapter = new PaperUserAdapter(player);

        return this.users.putIfAbsent(player.getUniqueId(), adapter);
    }

    @Override
    public PaperUserAdapter removeUser(@NotNull final UUID uuid) {
        return this.users.remove(uuid);
    }

    @Override
    public Optional<PaperUserAdapter> getUser(@NotNull final UUID uuid) {
        final Server server = this.plugin.getServer();

        final Player player = server.getPlayer(uuid);

        if (!this.users.containsKey(uuid) && player != null) {
            return Optional.of(addUser(player));
        }

        return Optional.of(this.users.get(uuid));
    }

    @Override
    public @NotNull final IUser getConsole() {
        return this.users.get(Pl3xMapPlugin.CONSOLE_UUID);
    }
}
package com.ryderbelserion.map.listeners;

import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.registry.UserRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class CacheListener implements Listener {

    private final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private final Pl3xMapCommon common = this.plugin.getPlugin();

    private final UserRegistry userRegistry = this.common.getUserRegistry();

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.userRegistry.addUser(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();

        this.userRegistry.removeUser(player);
    }
}
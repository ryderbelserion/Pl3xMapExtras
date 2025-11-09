package com.ryderbelserion.map.listeners.mobs;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.modules.mobs.MobRegistry;
import com.ryderbelserion.map.objects.MapPosition;
import net.kyori.adventure.key.Key;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

public class PaperMobListener implements Listener {

    private final MobRegistry registry;

    public PaperMobListener(@NotNull final Pl3xMapCommon plugin) {
        this.registry = plugin.getMobRegistry();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemove(final EntityRemoveFromWorldEvent event) {
        final Entity entity = event.getEntity();

        this.registry.removeMob(entity.getWorld().getName(), entity.getUniqueId());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntitySpawn(final EntitySpawnEvent event) {
        final Entity entity = event.getEntity();
        final EntityType entityType = entity.getType();
        final Key key = entityType.getKey();

        final String mobName = key.asMinimalString();

        final Location location = entity.getLocation();

        final MapPosition position = new MapPosition(location.getWorld().getName(), location.blockX(), location.blockY(), location.blockZ());

        this.registry.displayMob(entity.name(), mobName, entity.getUniqueId(), position, consumer -> {

        });
    }
}
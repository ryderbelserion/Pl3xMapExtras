package com.ryderbelserion.map.listeners.mobs;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.modules.mobs.MobRegistry;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;

public class PaperMobListener implements Listener {

    private final FusionCore fusion = FusionProvider.getInstance();

    private final MobRegistry registry;
    private final Pl3xMapCommon plugin;

    public PaperMobListener(@NotNull final Pl3xMapCommon plugin) {
        this.registry = plugin.getMobRegistry();
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRemove(final EntityRemoveFromWorldEvent event) {
        final Entity entity = event.getEntity();

        this.fusion.log("warn", "Entity Type Despawn: {}", entity.getType().getKey().asMinimalString());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntitySpawn(final EntitySpawnEvent event) {
        final Entity entity = event.getEntity();

        this.fusion.log("warn", "Entity Type Spawn: {}", entity.getType().getKey().asMinimalString());
    }
}
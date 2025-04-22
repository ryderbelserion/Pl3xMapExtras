package com.ryderbelserion.map.listener.mobs;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;

public class MobEntityListener implements Listener {

    private @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private @NotNull final Optional<MobsManager> mobsManager = this.plugin.getMobsManager();

    @EventHandler
    public void onEntityRemove(EntityRemoveFromWorldEvent event) {
        if (this.mobsManager.isEmpty()) return;

        final Entity entity = event.getEntity();

        if (entity instanceof Mob mob) {
            this.mobsManager.get().removeMarker(mob);
        }
    }
}
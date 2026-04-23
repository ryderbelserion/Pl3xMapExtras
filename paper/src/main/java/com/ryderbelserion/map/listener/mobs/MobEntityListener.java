package com.ryderbelserion.map.listener.mobs;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MobEntityListener implements Listener {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Pl3xMapPaper platform = this.plugin.getPlatform();

    private final BasicConfig config = this.platform.getBasicConfig();

    @EventHandler
    public void onEntityRemove(EntityRemoveFromWorldEvent event) {
        if (!this.config.isMobsEnabled()) return;

        this.platform.getMobsManager().ifPresent(mobs -> {
            final Entity entity = event.getEntity();

            if (entity instanceof Mob mob) {
                mobs.removeMarker(mob);
            }
        });
    }
}
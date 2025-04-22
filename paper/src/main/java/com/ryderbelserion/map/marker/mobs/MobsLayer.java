package com.ryderbelserion.map.marker.mobs;

import com.ryderbelserion.fusion.paper.api.enums.Scheduler;
import com.ryderbelserion.fusion.paper.api.scheduler.FoliaScheduler;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.config.MobConfig;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Collection;
import java.util.Collections;

public class MobsLayer extends WorldLayer {

    private @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private @NotNull final Server server = this.plugin.getServer();

    private @NotNull final ComponentLogger logger = this.plugin.getComponentLogger();

    private @Nullable final MobsManager mobsManager = this.plugin.getMobsManager();

    public static final String KEY = "pl3xmap_mobs";

    private final MobConfig config;

    public MobsLayer(@NotNull final MobConfig config) {
        super(KEY, config.getWorld(), () -> config.LAYER_LABEL);

        this.config = config;

        setShowControls(config.LAYER_SHOW_CONTROLS);
        setDefaultHidden(config.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(config.LAYER_UPDATE_INTERVAL);
        setPriority(config.LAYER_PRIORITY);
        setZIndex(config.LAYER_ZINDEX);
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        retrieveMarkers();

        if (this.mobsManager == null) { // return immutable empty list
            return Collections.emptyList();
        }

        return this.mobsManager.getActiveMarkers(getWorld().getName());
    }

    private void retrieveMarkers() {
        if (this.mobsManager == null) {
            this.logger.warn("The mob manager instance is null.");

            return;
        }

        final World bukkitWorld = this.server.getWorld(this.config.getWorld().getName());

        // If world is null, do fuck all.
        if (bukkitWorld == null) {
            return;
        }

        new FoliaScheduler(Scheduler.global_scheduler) {
            @Override
            public void run() {
                bukkitWorld.getEntitiesByClass(Mob.class).forEach(mob -> {
                    if (config.ONLY_SHOW_MOBS_EXPOSED_TO_SKY && bukkitWorld.getHighestBlockYAt(mob.getLocation()) > mob.getLocation().getY()) {
                        return;
                    }

                    String key = String.format("%s_%s_%s", KEY, getWorld().getName(), mob.getUniqueId());

                    mobsManager.addMarker(key, mob, config);
                });
            }
        }.runNow();
    }
}
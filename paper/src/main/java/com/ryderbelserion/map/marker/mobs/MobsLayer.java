package com.ryderbelserion.map.marker.mobs;

import com.ryderbelserion.fusion.paper.builders.folia.FoliaScheduler;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.config.MobConfig;
import net.kyori.adventure.key.Key;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MobsLayer extends WorldLayer {

    private @NotNull final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private @NotNull final Pl3xMapPaper platform = this.plugin.getPlatform();

    private @NotNull final Server server = this.plugin.getServer();

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

        final Map<Key, Collection<Marker<?>>> mobs = new ConcurrentHashMap<>();

        final Key worldKey = this.config.getWorldKey();

        this.platform.getMobsManager().ifPresent(mob -> mobs.put(worldKey, mob.getActiveMarkers(worldKey)));

        return mobs.get(worldKey);
    }

    private void retrieveMarkers() {
        this.platform.getMobsManager().ifPresent(mobs -> {
            final Key worldKey = this.config.getWorldKey();

            final World bukkitWorld = this.server.getWorld(worldKey);

            // If world is null, do fuck all.
            if (bukkitWorld == null) {
                return;
            }

            bukkitWorld.getEntitiesByClass(Mob.class).forEach(mob -> {
                final Location location = mob.getLocation();

                new FoliaScheduler(this.plugin, location.getWorld(), location.getBlockX(), location.getBlockZ()) {
                    @Override
                    public void run() {
                        if (config.ONLY_SHOW_MOBS_EXPOSED_TO_SKY && bukkitWorld.getHighestBlockYAt(location) > location.getY()) {
                            return;
                        }

                        String key = String.format("%s_%s_%s", KEY, getWorld().getName(), mob.getUniqueId());

                        mobs.addMarker(key, mob, config);
                    }
                }.runNow();
            });
        });
    }
}
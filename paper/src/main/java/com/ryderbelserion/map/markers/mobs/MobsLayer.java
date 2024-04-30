package com.ryderbelserion.map.markers.mobs;

import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.config.MobConfig;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import org.bukkit.World;
import org.bukkit.entity.Mob;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;

public class MobsLayer extends WorldLayer {

    private @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private @NotNull final MobsManager mobsManager = this.plugin.getMobsManager();

    public static final String KEY = "pl3xmap_mobs";

    private final MobConfig config;

    public MobsLayer(@NotNull MobConfig config) {
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

        return this.mobsManager.getActiveMarkers(getWorld().getName());
    }

    private void retrieveMarkers() {
        World bukkitWorld = this.plugin.getServer().getWorld(this.config.getWorld().getName());

        // If world is null, do fuck all.
        if (bukkitWorld == null) {
            return;
        }

        this.plugin.getServer().getGlobalRegionScheduler().execute(this.plugin, () -> bukkitWorld.getEntitiesByClass(Mob.class).forEach(mob -> {
            if (config.ONLY_SHOW_MOBS_EXPOSED_TO_SKY && bukkitWorld.getHighestBlockYAt(mob.getLocation()) > mob.getLocation().getY()) {
                return;
            }

            String key = String.format("%s_%s_%s", KEY, getWorld().getName(), mob.getUniqueId());

            this.mobsManager.addMarker(key, mob, this.config);
        }));
    }
}
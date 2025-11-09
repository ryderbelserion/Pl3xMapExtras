package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.constants.Namespaces;
import com.ryderbelserion.map.modules.mobs.interfaces.IMobLayer;
import com.ryderbelserion.map.modules.mobs.objects.Mob;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MobLayer extends WorldLayer implements IMobLayer {

    private final Map<Mob, Marker<?>> markers = new ConcurrentHashMap<>();

    private final FusionCore fusion = FusionProvider.getInstance();

    private final Pl3xMapCommon plugin;

    public MobLayer(@NotNull final Pl3xMapCommon plugin, @NotNull final World world) {
        super(Namespaces.mob_key, world, () -> plugin.getMobConfig().getLayerConfig().getLayerLabel());

        this.plugin = plugin;

        refresh();

        this.fusion.log("warn", "The mob layer for {} is ready!", world.getName());
    }

    @Override
    public void refresh() {
        final LayerConfig config = this.plugin.getMobConfig().getLayerConfig();

        setShowControls(config.isShowControls());
        setDefaultHidden(config.isHiddenByDefault());
        setPriority(config.getPriority());
        setZIndex(config.getIndex());
        setCss(config.getCss());

        final int interval = config.getUpdateInterval();

        if (interval == -1) {
            setLiveUpdate(true);
        } else {
            setUpdateInterval(interval);
        }
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        this.plugin.populateMobs(getWorld().getName()); // populate mobs

        return this.markers.values();
    }

    @Override
    public void displayMob(@NotNull final Mob mob) {
        mob.getIcon().ifPresent(icon -> this.markers.put(mob, icon));
    }

    @Override
    public void removeMob(@NotNull final String worldName, @NotNull final UUID uuid) {
        this.markers.keySet().removeIf(entry -> entry.getUuid().equals(uuid));
    }

    @Override
    public void purgeCache() {
        this.markers.clear();
    }
}
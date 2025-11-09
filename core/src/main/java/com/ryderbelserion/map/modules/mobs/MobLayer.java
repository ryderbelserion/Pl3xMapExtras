package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.constants.Namespaces;
import com.ryderbelserion.map.modules.mobs.config.MobConfig;
import com.ryderbelserion.map.modules.mobs.interfaces.IMobLayer;
import com.ryderbelserion.map.modules.mobs.objects.Mob;
import com.ryderbelserion.map.modules.mobs.objects.MobTexture;
import com.ryderbelserion.map.objects.MapPosition;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
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

        this.fusion.log("warn", "The mob layer for {} is ready!", world.getName());
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        this.plugin.populateMobs(getWorld().getName()); // populate mobs

        return this.markers.values();
    }

    @Override
    public void displayMob(@NotNull final Mob mob) {
        final MapPosition position = mob.position();
        final String worldName = position.worldName();
        final UUID uuid = mob.mobId();

        final String format = "%s_%s_%s".formatted(Namespaces.mob_key, worldName, uuid);

        final MobConfig config = this.plugin.getMobConfig();

        final MobTexture mobTexture = mob.texture();

        final String mobKey = mobTexture.getKey();

        final Icon icon = Marker.icon(format, position.asPoint(), mobKey, config.getIconVector());

        Options.Builder builder = new Options.Builder();

        builder.tooltipDirection(Tooltip.Direction.TOP).tooltipContent(config.getPopupContent().replace("<name>", mob.asPlainText()));

        icon.setOptions(builder.build());

        this.markers.put(mob, icon);
    }

    @Override
    public void removeMob(@NotNull final String worldName, @NotNull final UUID uuid) {
        this.markers.keySet().removeIf(entry -> entry.mobId().equals(uuid));
    }

    @Override
    public void purgeCache() {
        this.markers.clear();
    }
}
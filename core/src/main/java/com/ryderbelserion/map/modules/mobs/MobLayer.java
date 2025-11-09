package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.constants.Namespaces;
import com.ryderbelserion.map.modules.mobs.interfaces.IMobLayer;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class MobLayer extends WorldLayer implements IMobLayer {

    private final FusionCore fusion = FusionProvider.getInstance();

    private final Pl3xMapCommon plugin;

    public MobLayer(@NotNull final Pl3xMapCommon plugin, @NotNull final MobRegistry registry, @NotNull final World world) {
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

        init(registry, world);

        this.fusion.log("warn", "The mob layer for {} is ready!", world.getName());
    }

    public void init(@NotNull final MobRegistry registry, @NotNull final World world) {
        final String worldName = world.getName();
    }

    @Override
    public void addMob() {

    }

    @Override
    public void removeMob() {

    }
}
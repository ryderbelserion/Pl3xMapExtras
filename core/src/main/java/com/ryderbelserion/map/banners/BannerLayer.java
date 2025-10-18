package com.ryderbelserion.map.banners;

import com.ryderbelserion.map.constants.Banner;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;

public class BannerLayer extends WorldLayer {

    public BannerLayer(@NotNull final World world) {
        super(Banner.banner_key, world, () -> "Banners");

        setShowControls(true);
        setDefaultHidden(false);
        setUpdateInterval(5);
        setPriority(99);
        setZIndex(99);
        setCss("");
    }
}
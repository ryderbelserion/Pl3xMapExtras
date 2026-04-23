package com.ryderbelserion.map.common.modules.banners.interfaces;

import com.ryderbelserion.map.common.modules.banners.objects.Banner;
import org.jetbrains.annotations.NotNull;

public interface IBannerLayer {

    boolean displayBanner(@NotNull final Banner banner, final boolean index);

    boolean removeBanner(@NotNull final Banner banner, final boolean index);

    void refresh();

}
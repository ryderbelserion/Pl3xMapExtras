package com.ryderbelserion.map.common.modules.banners.interfaces;

import com.ryderbelserion.map.common.modules.banners.objects.Banner;
import org.jetbrains.annotations.NotNull;

public interface IBannerLayer {

    void displayBanner(@NotNull final Banner banner, final boolean cacheLookUp);

    boolean removeBanner(@NotNull final Banner banner, final boolean cacheLookUp);

    void refresh();

}
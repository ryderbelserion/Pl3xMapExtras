package com.ryderbelserion.map.modules.banners.interfaces;

import com.ryderbelserion.map.modules.banners.objects.Banner;
import org.jetbrains.annotations.NotNull;

public interface IBannerLayer {

    void displayBanner(@NotNull final Banner banner, final boolean cacheLookUp);

    void removeBanner(@NotNull final Banner banner, final boolean cacheLookUp);

    void refresh();

}
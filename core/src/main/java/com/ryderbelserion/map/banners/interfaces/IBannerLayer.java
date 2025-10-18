package com.ryderbelserion.map.banners.interfaces;

import com.ryderbelserion.map.banners.objects.Banner;
import org.jetbrains.annotations.NotNull;

public interface IBannerLayer {

    void displayBanner(@NotNull final Banner banner, final boolean cacheLookUp);

    void removeBanner(@NotNull final Banner banner, final boolean cacheLookUp);

}
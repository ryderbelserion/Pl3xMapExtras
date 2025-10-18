package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.map.banners.BannerListener;
import com.ryderbelserion.map.banners.BannerRegistry;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public abstract class Pl3xMapCommon {

    private final FileManager fileManager;
    private final FusionCore fusion;
    private final Path path;

    public Pl3xMapCommon(@NotNull final FusionCore fusion) {
        this.fileManager = fusion.getFileManager();
        this.path = fusion.getDataPath();
        this.fusion = fusion;
    }

    private BannerRegistry bannerRegistry;

    public void init() {
        this.bannerRegistry = new BannerRegistry(this);
        this.bannerRegistry.init();
    }

    public void reload() {

    }

    public void stop() {

    }

    public @NotNull final BannerRegistry getBannerRegistry() {
        return this.bannerRegistry;
    }

    public @NotNull final FileManager getFileManager() {
        return this.fileManager;
    }

    public @NotNull final FusionCore getFusion() {
        return this.fusion;
    }

    public @NotNull final Path getPath() {
        return this.path;
    }
}
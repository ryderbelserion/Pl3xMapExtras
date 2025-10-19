package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.enums.FileType;
import com.ryderbelserion.map.banners.config.BannerConfig;
import com.ryderbelserion.map.banners.BannerRegistry;
import com.ryderbelserion.map.banners.objects.BannerLocation;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.objects.MapParticle;
import com.ryderbelserion.map.objects.MapSound;
import net.kyori.adventure.audience.Audience;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
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
    private BannerConfig bannerConfig;

    public void init() {
        this.fileManager.extractFolder("banners", this.path)
                .extractFolder("storage", this.path);

        this.fileManager.addFile(this.path.resolve("storage").resolve("banners.json"), FileType.JSON)
                .addFile(this.path.resolve("banners").resolve("banners.yml"), FileType.YAML);

        this.bannerConfig = new BannerConfig();
        this.bannerConfig.init();

        if (this.bannerConfig.isEnabled()) {
            this.bannerRegistry = new BannerRegistry(this);
            this.bannerRegistry.init();
        } else {
            this.fusion.log("warn", "The banner module is not enabled!");
        }
    }

    public abstract void playParticle(@NotNull final BannerLocation location, @NotNull final MapParticle mapParticle);

    public void playSound(@NotNull final Audience audience, @NotNull final BannerLocation location, @NotNull final MapSound mapSound) {
        audience.playSound(mapSound.asSound(), location.x(), location.y(), location.z());
    }

    public void reload() {
        this.fusion.reload();

        if (this.bannerConfig != null) {
            this.bannerConfig.init();

            if (!this.bannerConfig.isEnabled()) {
                final Pl3xMap api = Pl3xMap.api();

                api.getWorldRegistry().forEach(world -> {
                    final Registry<@NotNull Layer> layerRegistry = world.getLayerRegistry();

                    if (layerRegistry.has(Namespaces.banner_key)) {
                        layerRegistry.unregister(Namespaces.banner_key);
                    }
                });
            }
        }
    }

    public void stop() {

    }

    public @NotNull final BannerRegistry getBannerRegistry() {
        return this.bannerRegistry;
    }

    public @NotNull final BannerConfig getBannerConfig() {
        return this.bannerConfig;
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
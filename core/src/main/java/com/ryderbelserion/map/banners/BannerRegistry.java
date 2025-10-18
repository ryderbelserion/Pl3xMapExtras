package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.banners.objects.Banner;
import com.ryderbelserion.map.banners.objects.BannerTexture;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.objects.Position;
import net.kyori.adventure.key.Key;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BannerRegistry {

    private final Map<String, BannerTexture> textures = new HashMap<>();

    private final FileManager fileManager;
    private final Pl3xMapCommon plugin;
    private final FusionCore fusion;
    private final Path path;

    public BannerRegistry(@NotNull final Pl3xMapCommon plugin) {
        this.fileManager = plugin.getFileManager();
        this.fusion = plugin.getFusion();
        this.path = plugin.getPath();

        this.plugin = plugin;
    }

    public void init() {
        this.fileManager.extractFolder("banners", this.path);

        for (final Path path : this.fusion.getFiles(this.path.resolve("banners"), ".png", 1)) {
            final String fileName = path.getFileName().toString().replace(".png", "");

            this.textures.putIfAbsent(fileName, new BannerTexture(path, fileName));
        }

        new BannerListener(this);

        this.fusion.log("info", "The banner module has been initialized!");
    }

    public @NotNull BannerTexture getTexture(@NotNull final Key key) {
        final String minimal = key.asMinimalString();
        final String value = minimal.endsWith("wall_banner") ? minimal.replace("_wall_banner", "") : minimal.replace("_banner", "");

        return this.textures.get(value);
    }

    public void addBanner(@NotNull final Position position, @NotNull final String displayName, @NotNull final String worldName, @NotNull final Key displayItem) {
        getLayer(worldName).ifPresentOrElse(layer -> {
            final Banner banner = new Banner(
                    getTexture(displayItem),
                    displayName,
                    worldName,
                    position
            );

            layer.displayBanner(banner, true);

            //todo() play particle
        }, () -> {
            this.fusion.log("warn", "Failed to add banner to %s @ (%s,%s,%s)".formatted(worldName, position.x(), position.y(), position.z()));
        });
    }

    public void removeBanner(@NotNull final Position position, @NotNull final String displayName, @NotNull final String displayItem, @NotNull final String worldName) {
        getLayer(worldName).ifPresentOrElse(layer -> {
            final Banner banner = new Banner(
                    getTexture(displayItem),
                    displayName,
                    worldName,
                    position
            );

            layer.removeBanner(banner, true);
        }, () -> {
            this.fusion.log("warn", "Could not remove banner from %s, because layer for the world does not exist.".formatted(worldName));
        });
    }

    public Optional<BannerLayer> getLayer(@NotNull final String worldName) {
        final World world = Pl3xMap.api().getWorldRegistry().get(worldName);

        if (world == null || !world.isEnabled()) {
            return Optional.empty();
        }

        return Optional.ofNullable((BannerLayer) world.getLayerRegistry().get(Namespaces.banner_key));
    }

    public @NotNull final BannerTexture getTexture(@NotNull final String fileName) {
        return this.textures.get(fileName);
    }

    public @NotNull final Map<String, BannerTexture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
package com.ryderbelserion.map.common.modules.banners;

import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.constants.Namespaces;
import com.ryderbelserion.map.api.registry.layers.AbstractLayerRegistry;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.modules.banners.objects.Banner;
import com.ryderbelserion.map.common.objects.MapTexture;
import com.ryderbelserion.map.api.objects.MapPosition;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BannerRegistry extends AbstractLayerRegistry<BannerLayer> {

    private final Pl3xMapPlugin plugin = (Pl3xMapPlugin) Pl3xMapExtras.Provider.getInstance();

    private final Map<String, MapTexture> textures = new HashMap<>();

    @Override
    public void init() {
        this.fileManager.extractFolder(this.source, "banners/icons", this.path);
    }

    @Override
    public void post() {
        for (final Path path : this.fusion.getFiles(this.path.resolve("banners").resolve("icons"), ".png")) {
            final String fileName = path.getFileName().toString().replace(".png", "");

            this.textures.putIfAbsent(fileName, MapTexture.of(path, "banner", fileName));
        }

        new BannerListener();

        this.fusion.log("info", "The banner module has been initialized!");
    }

    @Override
    public void reload() {
        this.fileManager.extractFolder(this.source, "banners/icons", this.path);

        super.reload();
    }

    public void addBanner(@NotNull final Audience audience, @NotNull final MapPosition position, @NotNull final String displayName, @NotNull final Key displayItem) {
        final String worldName = position.worldName();

        getLayer(worldName).ifPresentOrElse(layer -> {
            final Banner banner = Banner.of(getTexture(displayItem), displayName, position);

            if (layer.displayBanner(banner, true)) {
                final BannerConfig config = this.plugin.getBannerConfig();

                final MapPosition location = MapPosition.of(worldName, position.x(), position.y(), position.z());

                config.getSpawnParticle().ifPresent(particle -> this.plugin.playParticle(location, particle));
                config.getSpawnSound().ifPresent(sound -> this.plugin.playSound(audience, location, sound));
            }
        }, () -> this.fusion.log("warn", "Failed to add banner to %s @ (%s,%s,%s)".formatted(worldName, position.x(), position.y(), position.z())));
    }

    public void removeBanner(@NotNull final Audience audience, @NotNull final MapPosition position, @NotNull final String displayName, @NotNull final String displayItem) {
        final String worldName = position.worldName();

        getLayer(worldName).ifPresentOrElse(layer -> {
            final Banner banner = new Banner(
                    getTexture(displayItem),
                    displayName,
                    position
            );

            if (layer.removeBanner(banner, true)) {
                final BannerConfig config = this.plugin.getBannerConfig();

                final MapPosition location = MapPosition.of(worldName, position.x(), position.y(), position.z());

                config.getRemoveParticle().ifPresent(particle -> this.plugin.playParticle(location, particle));
                config.getRemoveSound().ifPresent(sound -> this.plugin.playSound(audience, location, sound));
            }
        }, () -> this.fusion.log("warn", "Could not remove banner from %s, because layer for the world does not exist.".formatted(worldName)));
    }

    @Override
    public @NotNull final Optional<BannerLayer> getLayer(@NotNull final String worldName) {
        final World world = Pl3xMap.api().getWorldRegistry().get(worldName);

        if (world == null || !world.isEnabled()) {
            return Optional.empty();
        }

        return Optional.ofNullable((BannerLayer) world.getLayerRegistry().get(Namespaces.banner_key));
    }

    @Override
    public final boolean isEnabled() {
        return this.plugin.getBannerConfig().isEnabled();
    }

    @Override
    public @NotNull final String getKey() {
        return Namespaces.banner_key;
    }

    public @NotNull final MapTexture getTexture(@NotNull final String fileName) {
        return this.textures.get(fileName);
    }

    public @NotNull MapTexture getTexture(@NotNull final Key key) {
        final String minimal = key.asMinimalString();
        final String value = minimal.endsWith("wall_banner") ? minimal.replace("_wall_banner", "") : minimal.replace("_banner", "");

        return getTexture(value);
    }

    public @NotNull final Map<String, MapTexture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
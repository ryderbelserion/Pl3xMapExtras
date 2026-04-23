package com.ryderbelserion.map.common.modules.banners;

import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.constants.Namespaces;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.modules.banners.objects.Banner;
import com.ryderbelserion.map.common.modules.banners.objects.BannerTexture;
import com.ryderbelserion.map.common.objects.MapPosition;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
import net.pl3x.map.core.registry.WorldRegistry;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BannerRegistry {

    private final Map<String, BannerTexture> textures = new HashMap<>();

    private final Pl3xMapPlugin plugin = (Pl3xMapPlugin) Pl3xMapExtras.Provider.getInstance();
    private final FusionKyori fusion = this.plugin.getFusion();
    private final Path path = this.plugin.getDataPath();

    public void init() {
        for (final Path path : this.fusion.getFiles(this.path.resolve("banners").resolve("icons"), ".png")) {
            final String fileName = path.getFileName().toString().replace(".png", "");

            this.textures.putIfAbsent(fileName, BannerTexture.of(path, fileName));
        }

        new BannerListener();

        this.fusion.log("info", "The banner module has been initialized!");
    }

    public void reload() {
        if (this.plugin.getBannerConfig().isEnabled()) {
            return;
        }

        final Pl3xMap api = Pl3xMap.api();

        final WorldRegistry registry = api.getWorldRegistry();

        for (final World world : registry.values()) {
            if (!world.isEnabled()) {
                continue;
            }

            final Registry<Layer> layer = world.getLayerRegistry();

            if (layer.has(Namespaces.banner_key)) {
                this.fusion.log("warn", "Unregistering the banner layer for %s".formatted(world.getName()));

                layer.unregister(Namespaces.banner_key);
            }
        }
    }

    public void addBanner(@NotNull final Audience audience, @NotNull final MapPosition position, @NotNull final String displayName, @NotNull final Key displayItem) {
        final String worldName = position.worldName();

        getLayer(worldName).ifPresentOrElse(layer -> {
            final Banner banner = Banner.of(getTexture(displayItem), displayName, position);

            layer.displayBanner(banner, true);

            final BannerConfig config = this.plugin.getBannerConfig();

            final MapPosition location = MapPosition.of(worldName, position.x(), position.y(), position.z());

            config.getSpawnParticle().ifPresent(particle -> this.plugin.playParticle(location, particle));
            config.getSpawnSound().ifPresent(sound -> this.plugin.playSound(audience, location, sound));
        }, () -> {
            this.fusion.log("warn", "Failed to add banner to %s @ (%s,%s,%s)".formatted(worldName, position.x(), position.y(), position.z()));
        });
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

    public @NotNull BannerTexture getTexture(@NotNull final Key key) {
        final String minimal = key.asMinimalString();
        final String value = minimal.endsWith("wall_banner") ? minimal.replace("_wall_banner", "") : minimal.replace("_banner", "");

        return getTexture(value);
    }

    public @NotNull final Map<String, BannerTexture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
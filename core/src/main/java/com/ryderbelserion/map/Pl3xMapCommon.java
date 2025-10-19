package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.enums.FileType;
import com.ryderbelserion.map.banners.config.BannerConfig;
import com.ryderbelserion.map.banners.BannerRegistry;
import com.ryderbelserion.map.banners.objects.BannerLocation;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.enums.Mode;
import com.ryderbelserion.map.objects.MapParticle;
import com.ryderbelserion.map.objects.MapSound;
import com.ryderbelserion.map.registry.MessageRegistry;
import com.ryderbelserion.map.registry.UserRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.markers.layer.Layer;
import net.pl3x.map.core.registry.Registry;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class Pl3xMapCommon {

    public static final UUID console = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final String namespace = "pl3xmapextras";

    private final FileManager fileManager;
    private final FusionCore fusion;
    private final Path path;

    public Pl3xMapCommon(@NotNull final FusionCore fusion) {
        this.fileManager = fusion.getFileManager();
        this.path = fusion.getDataPath();
        this.fusion = fusion;
    }

    private MessageRegistry messageRegistry;
    private UserRegistry userRegistry;

    private BannerRegistry bannerRegistry;
    private BannerConfig bannerConfig;

    public void init(@NotNull final Audience audience) {
        Pl3xMapProvider.register(this);

        this.fileManager.extractFolder("banners", this.path).extractFolder("storage", this.path);

        this.fileManager.addFile(this.path.resolve("storage").resolve("banners.json"), FileType.JSON)
                .addFile(this.path.resolve("banners").resolve("banners.yml"), FileType.YAML)
                .addFile(this.path.resolve("config.yml"), FileType.YAML)
                .addFile(this.path.resolve("messages.yml"), FileType.YAML)
                .addFolder(this.path.resolve("locale"), FileType.YAML);

        this.messageRegistry = new MessageRegistry();
        this.messageRegistry.init();

        this.userRegistry = new UserRegistry();
        this.userRegistry.init(audience);

        this.bannerConfig = new BannerConfig();
        this.bannerConfig.init();

        if (this.bannerConfig.isEnabled()) {
            this.bannerRegistry = new BannerRegistry(this);
            this.bannerRegistry.init();
        } else {
            this.fusion.log("warn", "The banner module is not enabled!");
        }

        registerCommands();
    }

    public void reload() {
        this.fusion.reload();

        this.fileManager.refresh(false);

        this.messageRegistry.init();

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

    public abstract boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission);

    public abstract void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description, @NotNull final Map<String, Boolean> children);

    public void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description) {
        registerPermission(mode, permission, description, new HashMap<>());
    }

    public abstract void broadcast(@NotNull final Component component, @NotNull final String permission);

    public void broadcast(@NotNull final Component component) {
        broadcast(component, "");
    }

    public abstract boolean isConsoleSender(@NotNull final Audience audience);

    public abstract void playParticle(@NotNull final BannerLocation location, @NotNull final MapParticle mapParticle);

    public abstract void registerCommands();

    public void playSound(@NotNull final Audience audience, @NotNull final BannerLocation location, @NotNull final MapSound mapSound) {
        audience.playSound(mapSound.asSound(), location.x(), location.y(), location.z());
    }

    public @NotNull final MessageRegistry getMessageRegistry() {
        return this.messageRegistry;
    }

    public @NotNull final BannerRegistry getBannerRegistry() {
        return this.bannerRegistry;
    }

    public @NotNull final UserRegistry getUserRegistry() {
        return this.userRegistry;
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
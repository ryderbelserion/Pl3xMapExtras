package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import com.ryderbelserion.fusion.files.enums.FileAction;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.adapters.IPlayerAdapter;
import com.ryderbelserion.map.common.api.FileKeys;
import com.ryderbelserion.map.common.api.adapters.PlayerAdapter;
import com.ryderbelserion.map.common.api.adapters.sender.ISenderAdapter;
import com.ryderbelserion.map.common.configs.ConfigManager;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.common.modules.banners.BannerRegistry;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.objects.MapParticle;
import com.ryderbelserion.map.common.objects.MapPosition;
import com.ryderbelserion.map.common.objects.MapSound;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class Pl3xMapPlugin extends Pl3xMapExtras {

    public static final UUID CONSOLE_UUID = new UUID(0, 0);
    public static final String CONSOLE_NAME = "Console";

    private BannerRegistry bannerRegistry;
    private ConfigManager configManager;
    private IPlayerAdapter<?> adapter;

    public Pl3xMapPlugin(@NotNull final FusionKyori fusion) {
        super(fusion);
    }

    public void playSound(@NotNull final Audience audience, @NotNull final MapPosition position, @NotNull final MapSound mapSound) {
        audience.playSound(mapSound.asSound(), position.x(), position.y(), position.z());
    }

    public abstract void playParticle(@NotNull final MapPosition position, @NotNull final MapParticle mapParticle);

    public abstract boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission);

    public abstract void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description, @NotNull final Map<String, Boolean> children);

    public void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description) {
        registerPermission(mode, permission, description, new HashMap<>());
    }

    public abstract ISenderAdapter getSenderAdapter();

    public abstract void registerCommands();

    @Override
    public void init() {
        this.fusion.init();

        Pl3xMapExtras.Provider.register(this);

        try {
            Files.createDirectories(this.dataPath);
        } catch (final IOException ignored) {}

        this.fileManager.addFolder(this.dataPath.resolve("locale"), FileType.YAML);

        final Path source = this.fileManager.getSource();

        this.fileManager.extractFolder(source, "storage", this.dataPath);
        this.fileManager.extractFolder(source, "banners", this.dataPath);

        List.of(
                FileKeys.banners_storage,
                FileKeys.banners_config
        ).forEach(file -> file.addFile(consumer -> consumer.addAction(FileAction.ALREADY_EXTRACTED)));

        List.of(
                FileKeys.config,
                FileKeys.messages
        ).forEach(FileKeys::addFile);

        this.configManager = new ConfigManager();
        this.configManager.init();

        this.fileManager.extractFolder(source, "banners/icons", this.dataPath);
        this.fileManager.extractFolder(source, "warps/icons", this.dataPath);
        this.fileManager.extractFolder(source, "mobs/icons", this.dataPath);
        this.fileManager.extractFolder(source, "signs/icons", this.dataPath);
    }

    @Override
    public void reload() {
        this.fusion.reload();

        this.fileManager.refresh(false).addFolder(this.dataPath.resolve("locale"), FileType.YAML);

        final Path source = this.fileManager.getSource();

        this.fileManager.extractFolder(source, "banners/icons", this.dataPath);
        this.fileManager.extractFolder(source, "warps/icons", this.dataPath);
        this.fileManager.extractFolder(source, "mobs/icons", this.dataPath);
        this.fileManager.extractFolder(source, "signs/icons", this.dataPath);
    }

    @Override
    public void post() {
        this.adapter = new PlayerAdapter<>(getUserRegistry(), getContextRegistry());

        if (getBannerConfig().isEnabled()) {
            this.bannerRegistry = new BannerRegistry();
            this.bannerRegistry.init();
        } else {
            this.fusion.log("warn", "The banner module is not enabled!");
        }
    }

    @Override
    public @NotNull <C> IPlayerAdapter<C> getPlayerAdapter(@NotNull final Class<C> object) {
        return (IPlayerAdapter<C>) this.adapter;
    }

    public @NotNull final BannerRegistry getBannerRegistry() {
        return this.bannerRegistry;
    }

    public @NotNull final BannerConfig getBannerConfig() {
        return this.configManager.getBannerConfig();
    }

    public @NotNull final BasicConfig getBasicConfig() {
        return this.configManager.getConfig();
    }

    public @NotNull final ConfigManager getConfigManager() {
        return this.configManager;
    }
}
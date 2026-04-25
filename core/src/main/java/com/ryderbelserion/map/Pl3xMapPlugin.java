package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.api.enums.Level;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.adapters.IPlayerAdapter;
import com.ryderbelserion.map.common.api.FileKeys;
import com.ryderbelserion.map.common.api.adapters.PlayerAdapter;
import com.ryderbelserion.map.common.api.adapters.sender.ISenderAdapter;
import com.ryderbelserion.map.common.commands.enums.Mode;
import com.ryderbelserion.map.common.configs.ConfigManager;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.common.modules.banners.BannerRegistry;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.objects.MapParticle;
import com.ryderbelserion.map.api.objects.MapPosition;
import com.ryderbelserion.map.common.objects.MapSound;
import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;
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

    public abstract void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description, @NotNull final Map<String, Boolean> children);

    public void registerPermission(@NotNull final Mode mode, @NotNull final String permission, @NotNull final String description) {
        registerPermission(mode, permission, description, new HashMap<>());
    }

    public void playSound(@NotNull final Audience audience, @NotNull final MapPosition position, @NotNull final MapSound mapSound) {
        audience.playSound(mapSound.asSound(), position.x(), position.y(), position.z());
    }

    public abstract void playParticle(@NotNull final MapPosition position, @NotNull final MapParticle mapParticle);

    public abstract boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission);

    public abstract ISenderAdapter getSenderAdapter();

    public abstract void registerCommands();

    @Override
    public void init() {
        this.fusion.init();

        Pl3xMapExtras.Provider.register(this);

        this.fileManager.addFolder(this.dataPath.resolve("locale"), FileType.YAML);

        List.of(
                FileKeys.banners_storage,
                FileKeys.banners_config,

                FileKeys.config,
                FileKeys.messages
        ).forEach(FileKeys::addFile);

        this.configManager = new ConfigManager();
        this.configManager.init();

        this.fileManager.extractFolder("warps/icons", this.dataPath);
        this.fileManager.extractFolder("signs/icons", this.dataPath);
        this.fileManager.extractFolder("mobs/icons", this.dataPath);
    }

    @Override
    public void reload() {
        this.fusion.reload();

        this.fileManager.refresh(false).addFolder(this.dataPath.resolve("locale"), FileType.YAML);

        this.configManager.init();

        this.fileManager.extractFolder("warps/icons", this.dataPath);
        this.fileManager.extractFolder("signs/icons", this.dataPath);
        this.fileManager.extractFolder("mobs/icons", this.dataPath);

        this.bannerRegistry.reload();
    }

    @Override
    public void post() {
        this.adapter = new PlayerAdapter<>(getUserRegistry(), getContextRegistry());

        this.bannerRegistry = new BannerRegistry();
        this.bannerRegistry.init();

        if (getBannerConfig().isEnabled()) {
            this.bannerRegistry.post();
        } else {
            this.fusion.log(Level.WARNING, "The banner module is not enabled!");
        }
    }

    @Override
    public @NotNull <C> IPlayerAdapter<C> getPlayerAdapter(@NotNull final Class<C> object) {
        return (IPlayerAdapter<C>) this.adapter;
    }

    public @NotNull final BannerRegistry getBannerRegistry() {
        return this.bannerRegistry;
    }

    public @NotNull final ConfigManager getConfigManager() {
        return this.configManager;
    }

    public @NotNull final BannerConfig getBannerConfig() {
        return this.configManager.getBannerConfig();
    }

    public @NotNull final BasicConfig getBasicConfig() {
        return this.configManager.getConfig();
    }
}
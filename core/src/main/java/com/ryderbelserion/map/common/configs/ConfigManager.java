package com.ryderbelserion.map.common.configs;

import com.ryderbelserion.map.common.api.FileKeys;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class ConfigManager {

    private BannerConfig bannerConfig;
    private BasicConfig config;

    public void init() {
        final CommentedConfigurationNode configuration = FileKeys.config.getYamlConfig();

        this.config = new BasicConfig(
                configuration
        );

        this.bannerConfig = new BannerConfig();
        this.bannerConfig.init();
    }

    public @NotNull final BannerConfig getBannerConfig() {
        return this.bannerConfig;
    }

    public @NotNull final BasicConfig getConfig() {
        return this.config;
    }
}
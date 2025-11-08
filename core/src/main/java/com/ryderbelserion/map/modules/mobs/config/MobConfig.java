package com.ryderbelserion.map.modules.mobs.config;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.Files;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.Optional;

public class MobConfig {

    private final FusionCore fusion = FusionProvider.getInstance();

    private LayerConfig layerConfig;

    private boolean isEnabled;

    private boolean isRequiredExposedToSky;

    public void init() {
        final CommentedConfigurationNode configuration = Files.mob_config.getYamlConfiguration().node("mob");

        this.isEnabled = configuration.node("enabled").getBoolean(false);

        if (!this.isEnabled) {
            return;
        }

        Optional.of(configuration.node("layer")).ifPresentOrElse(section -> {
            this.layerConfig = new LayerConfig(section);

            this.fusion.log("warn", "The layer section was found in banners.yml!");
        }, () -> {
            this.layerConfig = new LayerConfig();

            this.fusion.log("warn", "The layer section was not found in banners.yml, Default values are being used!");
        });

        this.isRequiredExposedToSky = configuration.node("only-show-mobs-exposed-to-sky").getBoolean(true);
    }

    public @NotNull final LayerConfig getLayerConfig() {
        return this.layerConfig;
    }

    public final boolean isRequiredExposedToSky() {
        return this.isRequiredExposedToSky;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }
}
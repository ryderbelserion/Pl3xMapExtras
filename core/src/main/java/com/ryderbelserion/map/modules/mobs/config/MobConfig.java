package com.ryderbelserion.map.modules.mobs.config;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.Files;
import net.pl3x.map.core.markers.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Optional;

public class MobConfig {

    private final FusionCore fusion = FusionProvider.getInstance();

    private LayerConfig layerConfig;

    private boolean isEnabled;

    private boolean isRequiredExposedToSky;

    private String popupContent;

    private Vector iconVector;

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

        this.popupContent = configuration.node("icon", "popup", "content").getString("<mob-id>");

        this.iconVector = getVector(configuration.node("icon", "size"), Vector.of(20, 20));
    }

    public @NotNull final LayerConfig getLayerConfig() {
        return this.layerConfig;
    }

    public @NotNull final String getPopupContent() {
        return this.popupContent;
    }

    public @NotNull final Vector getIconVector() {
        return this.iconVector;
    }

    public final boolean isRequiredExposedToSky() {
        return this.isRequiredExposedToSky;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }

    private @NotNull Vector getVector(@NotNull final CommentedConfigurationNode configuration, @NotNull final Vector defaultVector) {
        Vector safeVector;

        try {
            safeVector = configuration.get(Vector.class, defaultVector);
        } catch (final SerializationException exception) {
            safeVector = defaultVector;
        }

        final double x = safeVector.x();
        final double z = safeVector.z();

        if (x == -1 && z == -1) {
            safeVector = Vector.of(20, 20);
        }

        return safeVector;
    }
}
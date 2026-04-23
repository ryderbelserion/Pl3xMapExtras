package com.ryderbelserion.map.common.modules.banners.config.icons;

import com.ryderbelserion.map.common.modules.banners.config.icons.types.PopupConfig;
import com.ryderbelserion.map.common.modules.banners.config.icons.types.TooltipConfig;
import net.pl3x.map.core.markers.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class IconConfig {

    private final Vector shadowAnchorVector;
    private final Vector anchorVector;
    private final Vector iconVector;

    private final double rotationAngle;
    private final String rotationOrigin;

    private final Vector shadowVectorSize;
    private final String shadowVectorImage;

    private final TooltipConfig tooltipConfig;

    private final PopupConfig popupConfig;

    public IconConfig(@NotNull final CommentedConfigurationNode configuration) {
        final Vector defaultVector = Vector.of(-1, -1);

        this.shadowAnchorVector = getVector(configuration.node("shadow", "anchor-vector"), defaultVector);
        this.anchorVector = getVector(configuration.node("anchor"), defaultVector);
        this.iconVector = getVector(configuration.node("size"), Vector.of(32, 32));

        this.rotationAngle = configuration.node("rotation", "angle").getDouble(0.0);
        this.rotationOrigin = configuration.node("rotation", "origin").getString("");

        this.shadowVectorSize = getVector(configuration.node("shadow", "vector"), Vector.of(20, 20));
        this.shadowVectorImage = configuration.node("shadow", "image").getString("");

        this.tooltipConfig = new TooltipConfig(configuration.node("tooltip"));

        this.popupConfig = new PopupConfig(configuration.node("popup"));
    }

    public IconConfig() {
        final Vector defaultVector = Vector.of(-1, -1);

        this.shadowAnchorVector = defaultVector;
        this.anchorVector = defaultVector;

        this.iconVector = Vector.of(32, 32);

        this.rotationAngle = 0.0;
        this.rotationOrigin = "";

        this.shadowVectorSize = Vector.of(20, 20);
        this.shadowVectorImage = "";

        this.tooltipConfig = new TooltipConfig();
        this.popupConfig = new PopupConfig();
    }

    public @Nullable final String asShadowVectorImage() {
        return !this.shadowVectorImage.isEmpty() ? this.shadowVectorImage : null;
    }

    public @Nullable final Vector asShadowAnchorVector() {
        return this.shadowAnchorVector;
    }

    public @Nullable final Vector asShadowVectorSize() {
        return this.shadowVectorSize;
    }

    public @Nullable final Double getRotationAngle() {
        return this.rotationAngle > 0.0 ? this.rotationAngle : null;
    }

    public @NotNull final String getRotationOrigin() {
        return this.rotationOrigin;
    }

    public @Nullable final Vector asAnchorVector() {
        return this.anchorVector;
    }

    public @Nullable final Vector asIconVector() {
        return this.iconVector;
    }

    public @NotNull final TooltipConfig asToolTip() {
        return this.tooltipConfig;
    }

    public @NotNull final PopupConfig asPopup() {
        return this.popupConfig;
    }

    private @Nullable Vector getVector(@NotNull final CommentedConfigurationNode configuration, @NotNull final Vector defaultVector) {
        Vector safeVector;

        try {
            safeVector = configuration.get(Vector.class, defaultVector);
        } catch (final SerializationException exception) {
            safeVector = defaultVector;
        }

        final double x = safeVector.x();
        final double z = safeVector.z();

        if (x == -1 && z == -1) {
            safeVector = null;
        }

        return safeVector;
    }
}
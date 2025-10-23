package com.ryderbelserion.map.banners.config;

import net.pl3x.map.core.markers.Vector;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class IconConfig {

    private final Vector iconSize;
    private final Vector anchorSize;

    private final double rotationAngle;
    private final String rotationOrigin;

    private final String shadowImage;

    private final Vector shadowSize;
    private final Vector shadowAnchorSize;

    public IconConfig(@NotNull final CommentedConfigurationNode configuration) {
        this.anchorSize = new Vector(configuration.node("anchor", "x").getInt(-1),
                configuration.node("anchor", "z").getInt(-1));
        this.iconSize = new Vector(configuration.node("size", "x").getInt(32),
                configuration.node("size", "z").getInt(32));

        this.rotationAngle = configuration.node("rotation", "angle").getDouble(0.0);
        this.rotationOrigin = configuration.node("rotation", "origin").getString("");

        this.shadowAnchorSize = new Vector(configuration.node("shadow", "anchor-vector", "x").getInt(-1),
                configuration.node("shadow", "anchor-vector", "z").getInt(-1));
        this.shadowSize = new Vector(configuration.node("shadow", "vector", "x").getInt(20),
                configuration.node("shadow", "vector", "z").getInt(20));
        this.shadowImage = configuration.node("shadow", "image").getString("");
    }

    public IconConfig() {
        this.anchorSize = new Vector(-1, -1);
        this.iconSize = new Vector(32, 32);

        this.rotationAngle = 0.0;
        this.rotationOrigin = "";

        this.shadowAnchorSize = new Vector(-1, -1);
        this.shadowSize = new Vector(20, 20);
        this.shadowImage = "";
    }

    public @NotNull final Vector getShadowAnchorSize() {
        return this.shadowAnchorSize;
    }

    public @NotNull final String getRotationOrigin() {
        return this.rotationOrigin;
    }

    public @NotNull final String getShadowImage() {
        return this.shadowImage;
    }

    public @NotNull final Vector getAnchorSize() {
        return this.anchorSize;
    }

    public @NotNull final Vector getShadowSize() {
        return this.shadowSize;
    }

    public @NotNull final Vector getIconSize() {
        return this.iconSize;
    }

    public final double getRotationAngle() {
        return this.rotationAngle;
    }
}
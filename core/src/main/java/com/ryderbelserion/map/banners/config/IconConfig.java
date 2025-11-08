package com.ryderbelserion.map.banners.config;

import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Tooltip;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class IconConfig {

    private final Vector iconSize;
    private final Vector anchorSize;

    private final double rotationAngle;
    private final String rotationOrigin;

    private final String shadowImage;

    private final Vector shadowSize;
    private final Vector shadowAnchorSize;

    private final Tooltip.Direction toolTipDirection;

    private final String toolTipContent;
    private final String toolTipPane;

    private final Point toolTipPoint;

    private final boolean toolTipPermanent;

    private final boolean toolTipSticky;

    private final double toolTipOpacity;

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

        final CommentedConfigurationNode tooltip = configuration.node("tooltip");

        this.toolTipContent = tooltip.node("content").getString("");
        this.toolTipPane = tooltip.node("pane").getString("");

        try {
            this.toolTipDirection = tooltip.node("direction").get(Tooltip.Direction.class, Tooltip.Direction.TOP);
        } catch (final SerializationException exception) {
            throw new RuntimeException(exception);
        }

        try {
            this.toolTipPoint = tooltip.node("point").get(Point.class, new Point(0, -6));
        } catch (final SerializationException exception) {
            throw new RuntimeException(exception);
        }

        this.toolTipPermanent = tooltip.node("permanent").getBoolean(false);
        this.toolTipSticky = tooltip.node("sticky").getBoolean(false);
        this.toolTipOpacity = tooltip.node("opacity").getDouble(0.0);
    }

    public IconConfig() {
        this.anchorSize = new Vector(-1, -1);
        this.iconSize = new Vector(32, 32);

        this.rotationAngle = 0.0;
        this.rotationOrigin = "";

        this.shadowAnchorSize = new Vector(-1, -1);
        this.shadowSize = new Vector(20, 20);
        this.shadowImage = "";

        this.toolTipContent = "";
        this.toolTipPane = "";

        this.toolTipPoint = new Point(0, -6);

        this.toolTipDirection = Tooltip.Direction.TOP;

        this.toolTipPermanent = false;
        this.toolTipSticky = false;

        this.toolTipOpacity = 0.0;
    }

    public @NotNull final Tooltip.Direction getToolTipDirection() {
        return this.toolTipDirection;
    }

    public @NotNull final String getToolTipContent() {
        return this.toolTipContent;
    }

    public @NotNull final Point getToolTipPoint() {
        return this.toolTipPoint;
    }

    public @NotNull final String getToolTipPane() {
        return this.toolTipPane;
    }

    public final boolean isToolTipPermanent() {
        return this.toolTipPermanent;
    }

    public final double getToolTipOpacity() {
        return this.toolTipOpacity;
    }

    public final boolean isToolTipSticky() {
        return this.toolTipSticky;
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
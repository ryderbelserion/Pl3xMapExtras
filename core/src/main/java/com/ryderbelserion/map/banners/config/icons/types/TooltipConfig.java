package com.ryderbelserion.map.banners.config.icons.types;

import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.option.Tooltip;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class TooltipConfig {

    private Tooltip.Direction direction;

    private final String content;
    private final String pane;

    private Point point;

    private final boolean isPermanent;
    private final boolean isSticky;
    private final double opacity;

    public TooltipConfig(@NotNull final CommentedConfigurationNode configuration) {
        this.content = configuration.node("content").getString("");
        this.pane = configuration.node("pane").getString("");

        try {
            this.direction = configuration.node("direction").get(Tooltip.Direction.class, Tooltip.Direction.TOP);
        } catch (final SerializationException exception) {
            this.direction = Tooltip.Direction.TOP;
        }

        try {
            this.point = configuration.node("point").get(Point.class, Point.of(0, -6));
        } catch (final SerializationException exception) {
            this.point = Point.of(0, -6);
        }

        this.isPermanent = configuration.node("permanent").getBoolean(false);
        this.isSticky = configuration.node("sticky").getBoolean(false);
        this.opacity = configuration.node("opacity").getDouble(0.0);
    }

    public TooltipConfig() {
        this.content = "";
        this.pane = "";

        this.direction = Tooltip.Direction.TOP;

        this.point = Point.of(0, -6);

        this.isPermanent = false;
        this.isSticky = false;
        this.opacity = 0.0;
    }

    public @NotNull final Tooltip.Direction asDirection() {
        return this.direction;
    }

    public @Nullable final String asContent() {
        return !this.content.isEmpty() ? this.content : null;
    }

    public @Nullable final String asPane() {
        return !this.pane.isEmpty() ? this.pane : null;
    }

    public @Nullable final Point asPoint() {
        final Point point = this.point;

        return point.x() != -1 && point.z() != -1 ? point : null;
    }

    public @Nullable final Double getOpacity() {
        return this.opacity > 0.0 ? this.opacity : null;
    }

    public final boolean isPermanent() {
        return this.isPermanent;
    }

    public final boolean isSticky() {
        return this.isSticky;
    }
}
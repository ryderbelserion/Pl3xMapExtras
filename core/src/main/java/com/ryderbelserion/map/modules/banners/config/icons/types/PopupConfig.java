package com.ryderbelserion.map.modules.banners.config.icons.types;

import net.pl3x.map.core.markers.Point;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

public class PopupConfig {

    private final String content;
    private final String pane;

    private final Point offset;

    private final int maxWidth;
    private final int minWidth;

    private final int maxHeight;

    private final boolean isAutoPan;
    private final Point leftPoint;
    private final Point rightPoint;

    private final boolean isKeptInView;
    private final boolean hasCloseButton;
    private final boolean isAutoClose;

    private final boolean isCloseOnEscape;
    private final boolean isCloseOnClick;

    public PopupConfig(@NotNull final CommentedConfigurationNode configuration) {
        final Point defaultPoint = Point.of(-1, -1);

        this.content = configuration.node("content").getString("");
        this.pane = configuration.node("pane").getString("");

        this.offset = getPoint(configuration.node("offset"), defaultPoint);

        this.minWidth = configuration.node("min-width").getInt(-1);
        this.maxWidth = configuration.node("max-width").getInt(-1);

        this.maxHeight = configuration.node("max-height").getInt(-1);

        this.isAutoPan = configuration.node("auto-pan", "enabled").getBoolean(false);
        this.leftPoint = getPoint(configuration.node("auto-pan", "padding", "bottom-left"), defaultPoint);
        this.rightPoint = getPoint(configuration.node("auto-pan", "padding", "bottom-right"), defaultPoint);

        this.isKeptInView = configuration.node("keep-in-view").getBoolean(false);

        this.hasCloseButton = configuration.node("close-button").getBoolean(false);

        this.isAutoClose = configuration.node("auto-close").getBoolean(true);

        this.isCloseOnEscape = configuration.node("close-on-escape-key").getBoolean(true);

        this.isCloseOnClick = configuration.node("close-on-click").getBoolean(true);
    }

    public PopupConfig() {
        final Point defaultPoint = Point.of(-1, -1);

        this.content = "";
        this.pane = "";

        this.offset = defaultPoint;

        this.minWidth = -1;
        this.maxWidth = -1;

        this.maxHeight = -1;

        this.isAutoPan = false;

        this.leftPoint = defaultPoint;
        this.rightPoint = defaultPoint;

        this.isKeptInView = false;
        this.hasCloseButton = false;

        this.isAutoClose = true;
        this.isCloseOnEscape = true;
        this.isCloseOnClick = true;
    }

    public @Nullable final String asContent() {
        return !this.content.isEmpty() ? this.content : null;
    }

    public @Nullable final String asPane() {
        return !this.pane.isEmpty() ? this.pane : null;
    }

    public @Nullable final Point asOffset() {
        final Point point = this.offset;

        return point.x() != -1 && point.z() != -1 ? point : null;
    }

    public @Nullable final Point asLeftPoint() {
        return this.leftPoint;
    }

    public @Nullable final Point asRightPoint() {
        return this.rightPoint;
    }

    public final boolean hasCloseButton() {
        return this.hasCloseButton;
    }

    public final boolean isCloseOnEscape() {
        return this.isCloseOnEscape;
    }

    public final boolean isCloseOnClick() {
        return this.isCloseOnClick;
    }

    public final boolean isKeptInView() {
        return this.isKeptInView;
    }

    public final boolean isAutoClose() {
        return this.isAutoClose;
    }

    public boolean isAutoPan() {
        return this.isAutoPan;
    }

    public @Nullable final Integer getMaxHeight() {
        return this.maxHeight != -1 ? this.maxHeight : null;
    }

    public @Nullable final Integer getMaxWidth() {
        return this.maxWidth != -1 ? this.maxWidth : null;
    }

    public @Nullable final Integer getMinWidth() {
        return this.minWidth != -1 ? this.minWidth : null;
    }

    private @Nullable Point getPoint(@NotNull final CommentedConfigurationNode configuration, @NotNull final Point defaultPoint) {
        Point safePoint;

        try {
            safePoint = configuration.get(Point.class, defaultPoint);
        } catch (final SerializationException exception) {
            safePoint = defaultPoint;
        }

        final int x = safePoint.x();
        final int z = safePoint.z();

        if (x == -1 && z == -1) {
            safePoint = null;
        }

        return safePoint;
    }
}
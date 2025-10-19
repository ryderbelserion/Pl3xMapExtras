package com.ryderbelserion.map.banners.config;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class LayerConfig {

    private final String layerLabel;
    private final boolean showControls;
    private final boolean isHiddenByDefault;
    private final int updateInterval;
    private final int priority;
    private final int zIndex;

    private final String css;

    public LayerConfig(@NotNull final CommentedConfigurationNode configuration) {
        this.layerLabel = configuration.node("label").getString("Banners");
        this.showControls = configuration.node("show-controls").getBoolean(true);
        this.isHiddenByDefault = configuration.node("default-hidden").getBoolean(false);
        this.updateInterval = configuration.node("update-interval").getInt(5);
        this.priority = configuration.node("priority").getInt(99);
        this.zIndex = configuration.node("z-index").getInt(99);

        this.css = configuration.node("css").getString("");
    }

    public LayerConfig() {
        this.layerLabel = "Banners";
        this.showControls = true;
        this.isHiddenByDefault = false;
        this.updateInterval = 5;
        this.priority = 99;
        this.zIndex = 99;
        this.css = "";
    }

    public @NotNull final String getLayerLabel() {
        return this.layerLabel;
    }

    public final boolean isShowControls() {
        return this.showControls;
    }

    public final boolean isHiddenByDefault() {
        return this.isHiddenByDefault;
    }

    public final int getUpdateInterval() {
        return this.updateInterval;
    }

    public final int getPriority() {
        return this.priority;
    }

    public final int getIndex() {
        return this.zIndex;
    }

    public @NotNull final String getCss() {
        return this.css;
    }
}
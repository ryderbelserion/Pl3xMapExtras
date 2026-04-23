package com.ryderbelserion.map.common.configs.types;

import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;

public class BasicConfig {

    private final String prefix;

    private final boolean isSignsEnabled;
    private final boolean isMobsEnabled;
    private final boolean isWarpsEnabled;
    private final boolean isClaimsEnabled;

    public BasicConfig(@NotNull final CommentedConfigurationNode configuration) {
        final CommentedConfigurationNode root = configuration.node("root");

        this.prefix = root.node("prefix").getString("<dark_gray>[<red>Core<white>Craft<dark_gray>] <reset>");

        final CommentedConfigurationNode markers = configuration.node("marker");

        this.isSignsEnabled = markers.node("signs").getBoolean(true);
        this.isMobsEnabled = markers.node("mobs").getBoolean(true);
        this.isWarpsEnabled = markers.node("warps").getBoolean(false);
        this.isClaimsEnabled = markers.node("claims").getBoolean(false);
    }

    public final boolean isClaimsEnabled() {
        return this.isClaimsEnabled;
    }

    public final boolean isWarpsEnabled() {
        return this.isWarpsEnabled;
    }

    public final boolean isSignsEnabled() {
        return this.isSignsEnabled;
    }

    public final boolean isMobsEnabled() {
        return this.isMobsEnabled;
    }

    public @NotNull final String getPrefix() {
        return this.prefix;
    }
}
package com.ryderbelserion.map.util;

import com.ryderbelserion.map.config.PluginConfig;

public class ConfigUtil {

    public static boolean isBannersEnabled() {
        return PluginConfig.toggle_banners;
    }

    public static boolean isClaimsEnabled() {
        return PluginConfig.toggle_claims;
    }

    public static boolean isSignsEnabled() {
        return PluginConfig.toggle_signs;
    }

    public static boolean isWarpsEnabled() {
        return PluginConfig.toggle_warps;
    }

    public static boolean isMobsEnabled() {
        return PluginConfig.toggle_mobs;
    }
}
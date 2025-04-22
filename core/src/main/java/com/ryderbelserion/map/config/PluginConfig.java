package com.ryderbelserion.map.config;

import com.ryderbelserion.fusion.core.FusionCore;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.util.FileUtil;
import java.nio.file.Path;
import java.util.List;

public class PluginConfig extends AbstractConfig {

    private static final FusionCore provider = FusionCore.Provider.get();

    private static final Path path = provider.getDataPath();

    @Key("marker.banners")
    @Comment("Should we display banner markers on pl3xmap?")
    public static boolean toggle_banners = true;

    @Key("marker.signs")
    @Comment("Should we display sign markers on pl3xmap?")
    public static boolean toggle_signs = true;

    @Key("marker.mobs")
    @Comment("Should we display mob markers on pl3xmap?")
    public static boolean toggle_mobs = true;

    @Key("marker.warps")
    @Comment("Should we display warp markers on pl3xmap?")
    public static boolean toggle_warps = false;

    @Key("marker.claims")
    @Comment("Should we display claim markers on pl3xmap?")
    public static boolean toggle_claims = false;

    @Comment("The prefix in front of messages.")
    public static String msg_prefix = "<gray>[<red>Pl3xMapExtras<gray>]";

    @Comment("The message sent when reloading the plugin.")
    public static String reload_plugin = "{prefix} <green>You have reloaded the plugin.";

    @Comment("The message sent when the player does not have the correct permission.")
    public static String no_permission = "{prefix} <red>You do not have permission to perform this command.";

    @Comment("The message sent when doing /pl3xmapextras help")
    public static List<String> help_message = List.of(
            "{prefix} <red>/pl3xmapextras reload <gray>- <white>reloads the plugin.",
            "{prefix} <red>/pl3xmapextras help <gray>- <white>shows this menu."
    );

    public PluginConfig() {}

    private static final PluginConfig CONFIG = new PluginConfig();

    public static void reload() {
        FileUtil.extractFile(PluginConfig.class, "config.yml", path, false);

        CONFIG.reload(path.resolve("config.yml"), PluginConfig.class);
    }
}
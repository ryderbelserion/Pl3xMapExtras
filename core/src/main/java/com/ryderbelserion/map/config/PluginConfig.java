package com.ryderbelserion.map.config;

import com.ryderbelserion.map.Provider;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.util.FileUtil;
import java.nio.file.Path;
import java.util.List;

public class PluginConfig extends AbstractConfig {

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
        Path mainDir = Provider.getInstance().getDataFolder();
        FileUtil.extractFile(PluginConfig.class, "config.yml", mainDir, false);

        CONFIG.reload(mainDir.resolve("config.yml"), PluginConfig.class);
    }
}
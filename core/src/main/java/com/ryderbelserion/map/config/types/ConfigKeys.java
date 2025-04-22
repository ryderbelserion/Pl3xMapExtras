package com.ryderbelserion.map.config.types;

import ch.jalu.configme.Comment;
import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.properties.Property;
import java.util.List;
import static ch.jalu.configme.properties.PropertyInitializer.newListProperty;
import static ch.jalu.configme.properties.PropertyInitializer.newProperty;

public class ConfigKeys implements SettingsHolder {

    @Comment("Should we display banner markers?")
    public static final Property<Boolean> toggle_banners = newProperty("marker.banners", true);

    @Comment("Should we display sign markers?")
    public static final Property<Boolean> toggle_signs = newProperty("marker.signs", true);

    @Comment("Should we display mob markers?")
    public static final Property<Boolean> toggle_mobs = newProperty("marker.mobs", true);

    @Comment("Should we display warp markers?")
    public static final Property<Boolean> toggle_warps = newProperty("marker.warps", true);

    @Comment("Should we display claim markers?")
    public static final Property<Boolean> toggle_claims = newProperty("marker.claims", true);

    @Comment("The prefix in front of messages.")
    public static Property<String> msg_prefix = newProperty("messages.prefix", "<gray>[<red>Pl3xMapExtras<gray>]");

    @Comment("The message sent when reloading the plugin.")
    public static Property<String> reload_plugin = newProperty("messages.reloaded-plugin", "{prefix} <green>You have reloaded the plugin.");

    @Comment("The message sent when the player does not have the correct permission.")
    public static Property<String> no_permission = newProperty("messages.no-permission", "{prefix} <red>You do not have permission to perform this command.");

    @Comment("The message sent when doing /pl3xmapextras help")
    public static Property<List<String>> help_message = newListProperty("messages.help-message", List.of(
            "{prefix} <red>/pl3xmapextras reload <gray>- <white>reloads the plugin.",
            "{prefix} <red>/pl3xmapextras help <gray>- <white>shows this menu."
    ));
}
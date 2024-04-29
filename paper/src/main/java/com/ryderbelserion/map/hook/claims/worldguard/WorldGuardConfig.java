package com.ryderbelserion.map.hook.claims.worldguard;

import java.nio.file.Path;

import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.configuration.AbstractConfig;

@SuppressWarnings("CanBeFinal")
public final class WorldGuardConfig extends AbstractConfig {

    @Key("settings.layer.label")
    @Comment("Label for map layer")
    public static String LAYER_LABEL = "WorldGuard";
    @Key("settings.layer.show-controls")
    @Comment("Show controls for map layer")
    public static boolean LAYER_SHOW_CONTROLS = true;
    @Key("settings.layer.default-hidden")
    @Comment("Whether map layer is hidden by default")
    public static boolean LAYER_DEFAULT_HIDDEN = false;
    @Key("settings.layer.update-interval")
    @Comment("Update interval for map layer")
    public static int LAYER_UPDATE_INTERVAL = 30;
    @Key("settings.layer.priority")
    @Comment("Priority for map layer")
    public static int LAYER_PRIORITY = 10;
    @Key("settings.layer.z-index")
    @Comment("zIndex for map layer")
    public static int LAYER_ZINDEX = 10;

    @Key("settings.claim.stroke.color")
    @Comment("Stroke color (#AARRGGBB)")
    public static String MARKER_STROKE_COLOR = "#FF00FF00";
    @Key("settings.claim.stroke.weight")
    @Comment("Stroke weight")
    public static int MARKER_STROKE_WEIGHT = 3;
    @Key("settings.claim.fill.color")
    @Comment("Fill color (#AARRGGBB)")
    public static String MARKER_FILL_COLOR = "#3300FF00";
    @Key("settings.claim.popup.tooltip")
    @Comment("Popup for claims")
    public static String MARKER_POPUP = """
            <span style="font-weight:bold;"><regionname></span><br/>
            <owners><members><flags>""";

    @Key("settings.claim.popup.owners")
    @Comment("Popup text for owners if present")
    public static String MARKER_POPUP_OWNERS = """
            Owners: <span style="font-weight:bold;"><owners></span><br/>""";
    @Key("settings.claim.popup.members")
    @Comment("Popup text for members if present")
    public static String MARKER_POPUP_MEMBERS = """
            Members: <span style="font-weight:bold;"><members></span><br/>""";
    @Key("settings.claim.popup.flags")
    @Comment("Popup text for flags if present")
    public static String MARKER_POPUP_FLAGS = """
            Flags:<br/><span style="display:inline-block;margin-left:10px"><flags></span><br/>""";
    @Key("settings.claim.popup.flag-entry")
    @Comment("How each flag entry should look")
    public static String MARKER_POPUP_FLAGS_ENTRY = """
            <span style="font-weight:bold;"><flag></span>: <value>""";

    private static final WorldGuardConfig CONFIG = new WorldGuardConfig();

    public static void reload() {
        Path mainDir = Pl3xMapExtras.getPlugin(Pl3xMapExtras.class).getDataFolder().toPath();
        CONFIG.reload(mainDir.resolve("claims").resolve("worldguard.yml"), WorldGuardConfig.class);
    }
}
package com.ryderbelserion.map.hook.claims.griefdefender;

import java.nio.file.Path;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.configuration.AbstractConfig;

@SuppressWarnings("CanBeFinal")
public final class GriefDefenderConfig extends AbstractConfig {

    @Key("settings.layer.label")
    @Comment("Label for map layer")
    public static String LAYER_LABEL = "GriefDefender";
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

    @Key("settings.claim.basic.stroke.color")
    @Comment("Stroke color (#AARRGGBB)")
    public static String MARKER_BASIC_STROKE_COLOR = "#FF00FF00";
    @Key("settings.claim.basic.stroke.weight")
    @Comment("Stroke weight")
    public static int MARKER_BASIC_STROKE_WEIGHT = 3;
    @Key("settings.claim.basic.fill.color")
    @Comment("Fill color (#AARRGGBB)")
    public static String MARKER_BASIC_FILL_COLOR = "#3300FF00";
    @Key("settings.claim.basic.popup")
    @Comment("Popup for basic claims")
    public static String MARKER_BASIC_POPUP = """
            Claim Owner: <span style="font-weight:bold;"><owner></span>""";

    @Key("settings.claim.admin.stroke.color")
    @Comment("Stroke color (#AARRGGBB)")
    public static String MARKER_ADMIN_STROKE_COLOR = "#FFED7117";
    @Key("settings.claim.admin.stroke.weight")
    @Comment("Stroke weight")
    public static int MARKER_ADMIN_STROKE_WEIGHT = 3;
    @Key("settings.claim.admin.fill.color")
    @Comment("Fill color (#AARRGGBB)")
    public static String MARKER_ADMIN_FILL_COLOR = "#33ED7117";
    @Key("settings.claim.admin.popup")
    @Comment("Popup for admin claims")
    public static String MARKER_ADMIN_POPUP = """
            <span style="font-weight:bold;">Administrator Claim</span>""";

    private static final GriefDefenderConfig CONFIG = new GriefDefenderConfig();

    public static void reload() {
        final Path mainDir = Pl3xMapExtras.getPlugin(Pl3xMapExtras.class).getDataPath();

        CONFIG.reload(mainDir.resolve("claims").resolve("griefdefender.yml"), GriefDefenderConfig.class);
    }
}
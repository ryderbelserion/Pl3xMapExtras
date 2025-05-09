package com.ryderbelserion.map.hook.claims.plotsquared;

import java.nio.file.Path;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.configuration.AbstractConfig;

@SuppressWarnings("CanBeFinal")
public final class P2Config extends AbstractConfig {

    @Key("settings.layer.label")
    @Comment("Label for map layer")
    public static String LAYER_LABEL = "PlotSquared";
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

    @Key("settings.plot.stroke.color")
    @Comment("Stroke color (#AARRGGBB)")
    public static String MARKER_STROKE_COLOR = "#FF00FF00";
    @Key("settings.plot.stroke.weight")
    @Comment("Stroke weight")
    public static int MARKER_STROKE_WEIGHT = 3;
    @Key("settings.plot.fill.color")
    @Comment("Fill color (#AARRGGBB)")
    public static String MARKER_FILL_COLOR = "#3300FF00";
    @Key("settings.plot.popup")
    @Comment("Popup for plot")
    public static String MARKER_POPUP = """
            Plot Owner: <span style="font-weight:bold;"><owner></span>""";

    private static final P2Config CONFIG = new P2Config();

    public static void reload() {
        final Path mainDir = Pl3xMapExtras.getPlugin(Pl3xMapExtras.class).getDataPath();

        CONFIG.reload(mainDir.resolve("claims").resolve("plotsquared.yml"), P2Config.class);
    }
}
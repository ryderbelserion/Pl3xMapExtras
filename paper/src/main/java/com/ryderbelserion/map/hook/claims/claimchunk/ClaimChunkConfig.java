package com.ryderbelserion.map.hook.claims.claimchunk;

import java.nio.file.Path;

import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.configuration.AbstractConfig;

@SuppressWarnings("CanBeFinal")
public final class ClaimChunkConfig extends AbstractConfig {

    @Key("settings.layer.label")
    @Comment("Label for map layer")
    public static String LAYER_LABEL = "ClaimChunk";
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

    @Key("settings.claim.show-chunks")
    @Comment("Show all the chunks in each claim.")
    public static boolean SHOW_CHUNKS = true;
    @Key("settings.claim.stroke.color")
    @Comment("Stroke color (#AARRGGBB)")
    public static String MARKER_STROKE_COLOR = "#FF00FF00";
    @Key("settings.claim.stroke.weight")
    @Comment("Stroke weight")
    public static int MARKER_STROKE_WEIGHT = 3;
    @Key("settings.claim.fill.color")
    @Comment("Fill color (#AARRGGBB)")
    public static String MARKER_FILL_COLOR = "#3300FF00";
    @Key("settings.claim.popup")
    @Comment("Popup for basic claims")
    public static String MARKER_POPUP = """
            Claim Owner: <span style="font-weight:bold;"><owner></span>""";

    private static final ClaimChunkConfig CONFIG = new ClaimChunkConfig();

    public static void reload() {
        Path mainDir = Pl3xMapExtras.getPlugin(Pl3xMapExtras.class).getDataFolder().toPath();
        CONFIG.reload(mainDir.resolve("claims").resolve("claimchunk.yml"), ClaimChunkConfig.class);
    }
}
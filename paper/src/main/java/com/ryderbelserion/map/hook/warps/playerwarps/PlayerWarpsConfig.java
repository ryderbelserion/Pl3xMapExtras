package com.ryderbelserion.map.hook.warps.playerwarps;

import java.nio.file.Path;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.config.v1.WarpsConfig;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Tooltip;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("CanBeFinal")
public final class PlayerWarpsConfig extends WarpsConfig {

    @Key("layer.label")
    @Comment("Label for map layer")
    public static String LAYER_LABEL = "Player Warps";
    @Key("layer.show-controls")
    @Comment("Show controls for map layer")
    public static boolean LAYER_SHOW_CONTROLS = true;
    @Key("layer.default-hidden")
    @Comment("Whether map layer is hidden by default")
    public static boolean LAYER_DEFAULT_HIDDEN = false;
    @Key("layer.update-interval")
    @Comment("Update interval for map layer")
    public static int LAYER_UPDATE_INTERVAL = 30;
    @Key("layer.priority")
    @Comment("Priority for map layer")
    public static int LAYER_PRIORITY = 20;
    @Key("layer.z-index")
    @Comment("zIndex for map layer")
    public static int LAYER_ZINDEX = 20;
    @Key("layer.css")
    @Comment("Custom CSS for the sign's layer.")
    public static String LAYER_CSS = null;

    @Key("warp.show-locked")
    @Comment("Show locked warps on the map")
    public static boolean SHOW_LOCKED = true;

    @Key("marker.icon.image")
    @Comment("""
            The image to register for the icon.""")
    public static String ICON_IMAGE = "warp";
    @Key("marker.icon.size")
    @Comment("""
            The size (in pixels) the icon should be.""")
    public static Vector ICON_SIZE = new Vector(32, 32);
    @Key("marker.icon.anchor")
    @Comment("""
            The coordinates of the "tip" of the icon (relative to its top
            left corner). The icon will be aligned so that this point is at
            the marker's geographical location. Centered by default if size
            is specified, also can be set in CSS with negative margins.""")
    public static Vector ICON_ANCHOR = null;
    @Key("marker.icon.rotation-angle")
    @Comment("""
            The angle (in degrees) the icon should be rotated at. Rotation
            is in a clockwise direction with 0 being at the top.""")
    public static Double ICON_ROTATION_ANGLE = null;
    @Key("marker.icon.rotation-origin")
    @Comment("""
            The origin point on the icon for the center of rotation""")
    public static String ICON_ROTATION_ORIGIN = null;
    @Key("marker.icon.shadow-image")
    @Comment("""
            The image to register for the icon.""")
    public static String ICON_SHADOW_IMAGE = "shadow";
    @Key("marker.icon.shadow-size")
    @Comment("""
            The size (in pixels) the icon's shadow image should be.""")
    public static Vector ICON_SHADOW_SIZE = new Vector(32, 32);
    @Key("marker.icon.shadow-anchor")
    @Comment("""
            The coordinates of the "tip" of the shadow (relative to its
            top left corner) (the same as icon anchor if not specified).""")
    public static Vector ICON_SHADOW_ANCHOR = null;

    @Key("marker.tooltip.content")
    @Comment("""
            Contents of the icon's tooltip.""")
    public static String ICON_TOOLTIP_CONTENT = """
            <warp><br>
            <br>
            Owner: <owner><br>
            Visits: <visits><br>
            Description: <desc><br>
            Time Created: <date><br>
            Category: <category><br>
            Rates: <rates><br>
            Rates Avg: <rates-avg> (<rates-stars>)""";
    @Key("marker.tooltip.pane")
    @Comment("""
            Map pane where the tooltip will be added.""")
    public static String ICON_TOOLTIP_PANE = null;
    @Key("marker.tooltip.offset")
    @Comment("""
            Optional offset of the tooltip position.""")
    public static Point ICON_TOOLTIP_OFFSET = null;
    @Key("marker.tooltip.direction")
    @Comment("""
            Direction where to open the tooltip. Possible values are: right,
            left, top, bottom, center, auto. auto will dynamically switch
            between right and left according to the tooltip position
            on the map.""")
    public static Tooltip.Direction ICON_TOOLTIP_DIRECTION = Tooltip.Direction.TOP;
    @Key("marker.tooltip.permanent")
    @Comment("""
            Whether to open the tooltip permanently or only on mouseover.""")
    public static Boolean ICON_TOOLTIP_PERMANENT = null;
    @Key("marker.tooltip.sticky")
    @Comment("""
            If true, the tooltip will follow the mouse instead of being
            fixed at the feature center.""")
    public static Boolean ICON_TOOLTIP_STICKY = null;
    @Key("marker.tooltip.opacity")
    @Comment("""
            Tooltip container opacity.""")
    public static Double ICON_TOOLTIP_OPACITY = null;

    @Key("marker.popup.content")
    @Comment("""
            Contents of the icon's popup.""")
    public static String ICON_POPUP_CONTENT = null;
    @Key("marker.popup.pane")
    @Comment("""
            Map pane where the popup will be added.""")
    public static String ICON_POPUP_PANE = null;
    @Key("marker.popup.offset")
    @Comment("""
            The offset of the popup position.""")
    public static Point ICON_POPUP_OFFSET = null;
    @Key("marker.popup.max-width")
    @Comment("""
            Max width of the popup, in pixels.""")
    public static Integer ICON_POPUP_MAX_WIDTH = null;
    @Key("marker.popup.min-width")
    @Comment("""
            Min width of the popup, in pixels.""")
    public static Integer ICON_POPUP_MIN_WIDTH = null;
    @Key("marker.popup.max-height")
    @Comment("""
            If set, creates a scrollable container of the given height inside
            a popup if its content exceeds it. The scrollable container can
            be styled using the leaflet-popup-scrolled CSS class selector.""")
    public static Integer ICON_POPUP_MAX_HEIGHT = null;
    @Key("marker.popup.auto-pan-enabled")
    @Comment("""
            Set it to false if you don't want the map to do panning animation
            to fit the opened popup.""")
    public static Boolean ICON_POPUP_SHOULD_AUTO_PAN = null;
    @Key("marker.popup.auto-pan-padding-all")
    @Comment("""
            Equivalent of setting both top left and bottom right auto-pan
            padding to the same value.""")
    public static Point ICON_POPUP_AUTO_PAN_PADDING = null;
    @Key("marker.popup.auto-pan-padding-top-left")
    @Comment("""
            The margin between the popup and the top left corner of the
            map view after auto-panning was performed.""")
    public static Point ICON_POPUP_AUTO_PAN_PADDING_TOP_LEFT = null;
    @Key("marker.popup.auto-pan-padding-bottom-right")
    @Comment("""
            The margin between the popup and the bottom right corner of
            the map view after auto-panning was performed.""")
    public static Point ICON_POPUP_AUTO_PAN_PADDING_BOTTOM_RIGHT = null;
    @Key("marker.popup.should-keep-in-view")
    @Comment("""
            Set it to true if you want to prevent users from panning
            the popup off of the screen while it is open.""")
    public static Boolean ICON_POPUP_SHOULD_KEEP_IN_VIEW = null;
    @Key("marker.popup.show-close-button")
    @Comment("""
            Controls the presence of a close button in the popup.""")
    public static Boolean ICON_POPUP_CLOSE_BUTTON = null;
    @Key("marker.popup.should-auto-close")
    @Comment("""
            Set it to false if you want to override the default behavior
            of the popup closing when another popup is opened.""")
    public static Boolean ICON_POPUP_SHOULD_AUTO_CLOSE = null;
    @Key("marker.popup.should-close-on-escape-key")
    @Comment("""
            Set it to false if you want to override the default behavior
            of the ESC key for closing of the popup.""")
    public static Boolean ICON_POPUP_SHOULD_CLOSE_ON_ESCAPE_KEY = null;
    @Key("marker.popup.should-close-on-click")
    @Comment("""
            Set it if you want to override the default behavior of the
            popup closing when user clicks on the map. Defaults to true.""")
    public static Boolean ICON_POPUP_SHOULD_CLOSE_ON_CLICK = null;

    private static final PlayerWarpsConfig CONFIG = new PlayerWarpsConfig();

    public static void reload() {
        final Path mainDir = JavaPlugin.getPlugin(Pl3xMapExtras.class).getDataPath();

        CONFIG.reload(mainDir.resolve("playerwarps.yml"), PlayerWarpsConfig.class);

        registerIcon(ICON_IMAGE);
        registerIcon(ICON_SHADOW_IMAGE);
    }
}
/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryderbelserion.map.config;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import libs.org.simpleyaml.configuration.ConfigurationSection;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Tooltip;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BannerConfig extends AbstractConfig {

    private static final FusionCore provider = FusionProvider.getInstance();

    private static final Path path = provider.getDataPath();

    @Key("layer.label")
    @Comment("""
            Label for map layer""")
    public String LAYER_LABEL = "Banners";
    @Key("layer.show-controls")
    @Comment("""
            Show controls for map layer""")
    public boolean LAYER_SHOW_CONTROLS = true;
    @Key("layer.default-hidden")
    @Comment("""
            Whether map layer is hidden by default""")
    public boolean LAYER_DEFAULT_HIDDEN = false;
    @Key("layer.update-interval")
    @Comment("""
            Update interval for map layer""")
    public int LAYER_UPDATE_INTERVAL = 5;
    @Key("layer.priority")
    @Comment("""
            Priority for map layer""")
    public int LAYER_PRIORITY = 99;
    @Key("layer.z-index")
    @Comment("""
            zIndex for map layer""")
    public int LAYER_ZINDEX = 99;
    @Key("layer.css")
    @Comment("""
            Custom CSS for the sign's layer.""")
    public String LAYER_CSS = null;

    @Key("marker.icon.size")
    @Comment("""
            The size (in pixels) the icon should be.""")
    public Vector ICON_SIZE = new Vector(32, 32);
    @Key("marker.icon.anchor")
    @Comment("""
            The coordinates of the "tip" of the icon (relative to its top
            left corner). The icon will be aligned so that this point is at
            the marker's geographical location. Centered by default if size
            is specified, also can be set in CSS with negative margins.""")
    public Vector ICON_ANCHOR = null;
    @Key("marker.icon.rotation.angle")
    @Comment("""
            The angle (in degrees) the icon should be rotated at. Rotation
            is in a clockwise direction with 0 being at the top.""")
    public Double ICON_ROTATION_ANGLE = null;
    @Key("marker.icon.rotation.origin")
    @Comment("""
            The origin point on the icon for the center of rotation""")
    public String ICON_ROTATION_ORIGIN = null;
    @Key("marker.icon.shadow")
    @Comment("""
            The registered icon to use for a shadow image.""")
    public String ICON_SHADOW = null;
    @Key("marker.icon.shadow-size")
    @Comment("""
            The size (in pixels) the icon's shadow image should be.""")
    public Vector ICON_SHADOW_SIZE = new Vector(20, 20);
    @Key("marker.icon.shadow-anchor")
    @Comment("""
            The coordinates of the "tip" of the shadow (relative to its
            top left corner) (the same as icon anchor if not specified).""")
    public Vector ICON_SHADOW_ANCHOR = null;

    @Key("marker.tooltip.content")
    @Comment("""
            Contents of the icon's popup.""")
    public String ICON_TOOLTIP_CONTENT = """
            <center><name></center>""";
    @Key("marker.tooltip.pane")
    @Comment("""
            Map pane where the tooltip will be added.""")
    public String ICON_TOOLTIP_PANE = null;
    @Key("marker.tooltip.offset")
    @Comment("""
            Optional offset of the tooltip position.""")
    public Point ICON_TOOLTIP_OFFSET = new Point(0, -6);
    @Key("marker.tooltip.direction")
    @Comment("""
            Direction where to open the tooltip. Possible values are: right,
            left, top, bottom, center, auto. auto will dynamically switch
            between right and left according to the tooltip position
            on the map.""")
    public Tooltip.Direction ICON_TOOLTIP_DIRECTION = Tooltip.Direction.TOP;
    @Key("marker.tooltip.permanent")
    @Comment("""
            Whether to open the tooltip permanently or only on mouseover.""")
    public Boolean ICON_TOOLTIP_PERMANENT = null;
    @Key("marker.tooltip.sticky")
    @Comment("""
            If true, the tooltip will follow the mouse instead of being
            fixed at the feature center.""")
    public Boolean ICON_TOOLTIP_STICKY = null;
    @Key("marker.tooltip.opacity")
    @Comment("""
            Tooltip container opacity.""")
    public Double ICON_TOOLTIP_OPACITY = null;

    @Key("marker.popup.content")
    @Comment("""
            Contents of the icon's popup.""")
    public String ICON_POPUP_CONTENT = null;
    @Key("marker.popup.pane")
    @Comment("""
            Map pane where the popup will be added.""")
    public String ICON_POPUP_PANE = null;
    @Key("marker.popup.offset")
    @Comment("""
            The offset of the popup position.""")
    public Point ICON_POPUP_OFFSET = null;
    @Key("marker.popup.max-width")
    @Comment("""
            Max width of the popup, in pixels.""")
    public Integer ICON_POPUP_MAX_WIDTH = null;
    @Key("marker.popup.min-width")
    @Comment("""
            Min width of the popup, in pixels.""")
    public Integer ICON_POPUP_MIN_WIDTH = null;
    @Key("marker.popup.max-height")
    @Comment("""
            If set, creates a scrollable container of the given height inside
            a popup if its content exceeds it. The scrollable container can
            be styled using the leaflet-popup-scrolled CSS class selector.""")
    public Integer ICON_POPUP_MAX_HEIGHT = null;
    @Key("marker.popup.auto-pan.enabled")
    @Comment("""
            Set it to false if you don't want the map to do panning animation
            to fit the opened popup.""")
    public Boolean ICON_POPUP_SHOULD_AUTO_PAN = null;
    @Key("marker.popup.auto-pan.padding.all")
    @Comment("""
            Equivalent of setting both top left and bottom right auto-pan
            padding to the same value.""")
    public Point ICON_POPUP_AUTO_PAN_PADDING_TOP_LEFT = null;
    @Key("marker.popup.auto-pan.padding.top-left")
    @Comment("""
            The margin between the popup and the top left corner of the
            map view after auto-panning was performed.""")
    public Point ICON_POPUP_AUTO_PAN_PADDING_BOTTOM_RIGHT = null;
    @Key("marker.popup.auto-pan.padding.bottom-right")
    @Comment("""
            The margin between the popup and the bottom right corner of
            the map view after auto-panning was performed.""")
    public Point ICON_POPUP_AUTO_PAN_PADDING = null;
    @Key("marker.popup.should-keep-in-view")
    @Comment("""
            Set it to true if you want to prevent users from panning
            the popup off of the screen while it is open.""")
    public Boolean ICON_POPUP_SHOULD_KEEP_IN_VIEW = null;
    @Key("marker.popup.show-close-button")
    @Comment("""
            Controls the presence of a close button in the popup.""")
    public Boolean ICON_POPUP_CLOSE_BUTTON = null;
    @Key("marker.popup.should-auto-close")
    @Comment("""
            Set it to false if you want to override the default behavior
            of the popup closing when another popup is opened.""")
    public Boolean ICON_POPUP_SHOULD_AUTO_CLOSE = null;
    @Key("marker.popup.should-close-on-escape-key")
    @Comment("""
            Set it to false if you want to override the default behavior
            of the ESC key for closing of the popup.""")
    public Boolean ICON_POPUP_SHOULD_CLOSE_ON_ESCAPE_KEY = null;
    @Key("marker.popup.should-close-on-click")
    @Comment("""
            Set it if you want to override the default behavior of the
            popup closing when user clicks on the map. Defaults to true.""")
    public Boolean ICON_POPUP_SHOULD_CLOSE_ON_CLICK = null;

    @Key("root.banners.block-place")
    @Comment("Should banners be displayed on block place?")
    public static boolean banners_block_place = true;

    private final World world;

    public BannerConfig(@NotNull World world) {
        this.world = world;
        reload();
    }

    public @NotNull World getWorld() {
        return this.world;
    }

    public void reload() {
        reload(path.resolve("banners").resolve("config.yml"), BannerConfig.class);
    }

    @Override
    protected @NotNull Object getClassObject() {
        return this;
    }

    @Override
    protected @Nullable Object getValue(@NotNull final String path, @Nullable final Object def) {
        if (getConfig().get("world-settings.default." + path) == null) {
            set("world-settings.default." + path, def);
        }

        return get("world-settings." + this.world.getName() + "." + path, get("world-settings.default." + path, def));
    }

    @Override
    protected void setComment(@NotNull final String path, @Nullable final String comment) {
        getConfig().setComment("world-settings.default." + path, comment);
    }

    @Override
    protected @Nullable Object get(@NotNull final String path) {
        final Object value = getConfig().get(path);

        if (value == null) {
            return null;
        }

        //noinspection EnhancedSwitchMigration
        switch (path.substring(path.indexOf(".", path.indexOf(".") + 1) + 1)) {
            case "marker.icon.size":
            case "marker.icon.anchor":
            case "marker.icon.shadow-size":
            case "marker.icon.shadow-anchor":
                if (value instanceof ConfigurationSection section) {
                    return Vector.of(section.getDouble("x"), section.getDouble("z"));
                } else if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Double> vector = (Map<String, Double>) value;
                    return Vector.of(vector.get("x"), vector.get("z"));
                }
                break;
            case "marker.tooltip.offset":
            case "marker.popup.offset":
            case "marker.popup.auto-pan.padding.all":
            case "marker.popup.auto-pan.padding.top-left":
            case "marker.popup.auto-pan.padding.bottom-right":
                if (value instanceof ConfigurationSection section) {
                    return Point.of(section.getInt("x"), section.getInt("z"));
                } else if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    Map<String, Integer> point = (Map<String, Integer>) value;
                    return Point.of(point.get("x"), point.get("z"));
                }
                break;
            case "marker.tooltip.direction":
                return Tooltip.Direction.valueOf(String.valueOf(value).toUpperCase(Locale.ROOT));
        }

        return super.get(path);
    }

    @Override
    protected void set(@NotNull String path, @Nullable Object value) {
        if (value instanceof Point(int x, int z)) {
            value = Map.of("x", x, "z", z);
        } else if (value instanceof Vector(double x, double z)) {
            value = Map.of("x", x, "z", z);
        } else if (value instanceof Tooltip.Direction direction) {
            value = direction.name();
        }

        getConfig().set(path, value);
    }
}
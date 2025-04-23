package com.ryderbelserion.map.config.types;

import ch.jalu.configme.SettingsHolder;
import ch.jalu.configme.configurationdata.CommentsConfiguration;
import ch.jalu.configme.properties.Property;
import com.ryderbelserion.map.config.types.objects.MapSettings;
import org.jetbrains.annotations.NotNull;
import static ch.jalu.configme.properties.PropertyInitializer.newBeanProperty;

public class BannerConfig implements SettingsHolder {

    @Override
    public void registerComments(@NotNull CommentsConfiguration conf) {
        conf.setComment("layer", "The configuration handling the Banner Layer!");
    }

    public static final Property<MapSettings> banner_settings = newBeanProperty(MapSettings.class, "layer", new MapSettings("Banners"));

    //@Comment("Should banners be displayed on block place?")
    //public static final Property<Boolean> banners_block_place = newProperty("root.banners.block-place", true);

    //@Comment("The label for the map layer!")
    //public static final Property<String> layer_label = newProperty("layer.label", "Banners");

    /*@Comment("Shows controls for the map layer.")
    public static final Property<Boolean> layer_show_controls = newProperty("layer.show-controls", false);

    @Comment("Whether the map layer is hidden by default.")
    public static final Property<Boolean> layer_default_hidden = newProperty("layer.default-hidden", false);

    @Comment("Update interval for the map layer.")
    public static final Property<Integer> layer_update_interval = newProperty("layer.update-interval", 5);

    @Comment("The priority for the layer")
    public static final Property<Integer> layer_priority = newProperty("layer.priority", 1);

    @Comment("The z-index for the map layer.")
    public static final Property<Integer> layer_z_index = newProperty("layer.z-index", 1);

    @Comment("Custom CSS for the banner layer")
    public static final Property<String> layer_css = newProperty("layer.css", "banner");

    @Comment("The size (in pixels) the icon should be.")
    public static final Property<Vector> icon_size = newBeanProperty(Vector.class, "marker.icon.size", new Vector(32, 32));

    @Comment("Whether the icon anchor size changes is enabled.")
    public static final Property<Boolean> icon_anchor_enabled = newProperty("marker.icon.anchor.toggle", false);

    @Comment({
            "The coordinates of the `tip` of the icon (relative to its top left corner).",
            "The icon will be aligned so that this point is at marker's geographical location. Centered by default if size is specified, also can be set in CSS with negative margins.",
    })
    public static final Property<Vector> icon_anchor = newBeanProperty(Vector.class, "marker.icon.anchor.vector", new Vector(32, 32));

    @Comment("The angle (in degrees) the icon should be rotated at. Rotation is in a clockwise direction with 0 being at the top.")
    public static final Property<Double> icon_rotation_angle = newProperty("marker.icon.rotation.angle", 0.0);

    @Comment("The origin point on the icon for the center of rotation")
    public static final Property<String> icon_rotation_origin = newProperty("marker.icon.rotation.origin", "");

    @Comment("The registered icon to use for a shadow image.")
    public static final Property<String> icon_shadow = newProperty("marker.icon.shadow", "");

    @Comment("The size (in pixels) the icon's shadow image should be.")
    public static final Property<Vector> icon_shadow_size = newBeanProperty(Vector.class, "marker.icon.shadow-size", new Vector(20, 20));

    @Comment("")
    public static final Property<Boolean> icon_shadow_anchor_enabled = newProperty("marker.icon.shadow-anchor.toggle", false);

    @Comment("The coordinates of the `tip` of the shadow (relative to its top left corner) (the same as icon anchor if not specified).")
    public static final Property<Vector> icon_shadow_anchor  = newBeanProperty(Vector.class, "marker.icon.shadow-anchor.vector", new Vector(32, 32));*/

}
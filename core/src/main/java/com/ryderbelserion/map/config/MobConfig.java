package com.ryderbelserion.map.config;

import com.ryderbelserion.fusion.core.FusionCore;
import libs.org.simpleyaml.configuration.ConfigurationSection;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.nio.file.Path;
import java.util.Map;

public class MobConfig extends AbstractConfig {

    private static final FusionCore provider = FusionCore.Provider.get();

    private static final Path path = provider.getDataPath();

    @Key("layer.label")
    @Comment("""
            Label for map layer""")
    public String LAYER_LABEL = "Mobs";
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
    public int LAYER_UPDATE_INTERVAL = 1;
    @Key("layer.priority")
    @Comment("""
            Priority for map layer""")
    public int LAYER_PRIORITY = 99;
    @Key("layer.z-index")
    @Comment("""
            zIndex for map layer""")
    public int LAYER_ZINDEX = 1;

    @Key("marker.icon.size")
    @Comment("""
            The size (in pixels) the icon should be.""")
    public Vector ICON_SIZE = new Vector(20, 20);

    @Key("marker.popup.content")
    @Comment("""
            Contents of the icon's popup.""")
    public String ICON_TOOLTIP_CONTENT = "<mob-id>";

    @Key("only-show-mobs-exposed-to-sky")
    @Comment("""
            Only show mobs that are exposed to the sky.""")
    public boolean ONLY_SHOW_MOBS_EXPOSED_TO_SKY = true;

    private final World world;

    public MobConfig(@NotNull World world) {
        this.world = world;

        reload();
    }

    public @NotNull World getWorld() {
        return this.world;
    }

    public void reload() {
        reload(path.resolve("mobs").resolve("config.yml"), MobConfig.class);
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

        if ("marker.icon.size".equals(path.substring(path.indexOf(".", path.indexOf(".") + 1) + 1))) {
            if (value instanceof ConfigurationSection section) {
                return Vector.of(section.getDouble("x"), section.getDouble("z"));
            } else if (value instanceof Map<?, ?>) {
                Map<String, Double> map = (Map<String, Double>) value;

                return Vector.of(map.get("x"), map.get("z"));
            }
        }

        return super.get(path);
    }

    @Override
    protected void set(@NotNull String path, @Nullable Object value) {
        if (value instanceof Vector(double x, double z)) {
            value = Map.of("x", x, "z", z);
        }

        getConfig().set(path, value);
    }
}
package com.ryderbelserion.map.config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.map.util.ConfigUtil;
import libs.org.simpleyaml.configuration.ConfigurationSection;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.configuration.AbstractConfig;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.option.Tooltip;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class WarpsConfig extends AbstractConfig {

    private static final FusionCore provider = FusionCore.Provider.get();

    private static final Path path = provider.getPath();

    private static final Logger logger = provider.getLogger();

    public static void registerIcon(@NotNull final String image) {
        if (!ConfigUtil.isWarpsEnabled()) return;

        final String fileName = String.format("icons%s%s.png", File.separator, image);
        final File icon = path.resolve("warps").resolve(fileName).toFile();

        try {
            final String key = String.format("pl3xmap_warps_%s", image);
            Pl3xMap.api().getIconRegistry().register(new IconImage(key, ImageIO.read(icon), "png"));
        } catch (final IOException exception) {
            logger.warning(String.format("Failed to register icon (%s) %s, %s", image, fileName, exception.getMessage()));
        }
    }

    @Override
    protected @Nullable Object get(@NotNull final String path) {
        final Object value = getConfig().get(path);

        if (value == null) {
            return null;
        }

        //noinspection EnhancedSwitchMigration
        switch (path) {
            case "marker.icon.size":
            case "marker.icon.anchor":
            case "marker.icon.shadow-size":
            case "marker.icon.shadow-anchor":
                if (value instanceof ConfigurationSection section) {
                    return Vector.of(section.getDouble("x"), section.getDouble("z"));
                } else if (value instanceof Map<?, ?>) {
                    @SuppressWarnings("unchecked")
                    final Map<String, Double> vector = (Map<String, Double>) value;

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
                    final Map<String, Integer> point = (Map<String, Integer>) value;

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

        super.set(path, value);
    }
}
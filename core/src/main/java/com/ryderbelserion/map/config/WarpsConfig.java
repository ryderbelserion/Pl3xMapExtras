package com.ryderbelserion.map.config;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import javax.imageio.ImageIO;
import com.ryderbelserion.map.Provider;
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

    public static void registerIcon(String image) {
        String fileName = String.format("icons%s%s.png", File.separator, image);
        File icon = Provider.getInstance().getDataFolder().resolve("warps").resolve("icons").resolve(fileName).toFile();

        try {
            String key = String.format("pl3xmap_warps_%s", image);
            Pl3xMap.api().getIconRegistry().register(new IconImage(key, ImageIO.read(icon), "png"));
        } catch (IOException e) {
            Provider.getInstance().getLogger().warning("Failed to register icon (" + image + ") " + fileName);

            e.printStackTrace();
        }
    }

    @Override
    protected @Nullable Object get(@NotNull String path) {
        Object value = getConfig().get(path);

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
        if (value instanceof Point point) {
            value = Map.of("x", point.x(), "z", point.z());
        } else if (value instanceof Vector vector) {
            value = Map.of("x", vector.x(), "z", vector.z());
        } else if (value instanceof Tooltip.Direction direction) {
            value = direction.name();
        }

        super.set(path, value);
    }
}
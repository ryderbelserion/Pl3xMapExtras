package com.ryderbelserion.map.marker.banners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import javax.imageio.ImageIO;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.registry.IconRegistry;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Icon {

    BLACK, BLUE, BROWN, CYAN, GREEN, GREY, LIGHT_BLUE, LIGHT_GREY, LIME, MAGENTA, ORANGE, PINK, PURPLE, RED, YELLOW, WHITE;

    private final String key;
    private final String type;

    Icon() {
        this.type = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_banner", this.type);
    }

    public @NotNull final String getKey() {
        return this.key;
    }

    private static @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private static @NotNull final Path path = plugin.getDataPath();

    private static @NotNull final ComponentLogger logger = plugin.getComponentLogger();

    public static @Nullable Icon get(@NotNull final Material type) {
        return switch (type) {
            case BLACK_BANNER, BLACK_WALL_BANNER -> BLACK;
            case BLUE_BANNER, BLUE_WALL_BANNER -> BLUE;
            case BROWN_BANNER, BROWN_WALL_BANNER -> BROWN;
            case CYAN_BANNER, CYAN_WALL_BANNER -> CYAN;
            case GREEN_BANNER, GREEN_WALL_BANNER -> GREEN;
            case GRAY_BANNER, GRAY_WALL_BANNER -> GREY;
            case LIGHT_BLUE_BANNER, LIGHT_BLUE_WALL_BANNER -> LIGHT_BLUE;
            case LIGHT_GRAY_BANNER, LIGHT_GRAY_WALL_BANNER -> LIGHT_GREY;
            case LIME_BANNER, LIME_WALL_BANNER -> LIME;
            case MAGENTA_BANNER, MAGENTA_WALL_BANNER -> MAGENTA;
            case ORANGE_BANNER, ORANGE_WALL_BANNER -> ORANGE;
            case PINK_BANNER, PINK_WALL_BANNER -> PINK;
            case PURPLE_BANNER, PURPLE_WALL_BANNER -> PURPLE;
            case RED_BANNER, RED_WALL_BANNER -> RED;
            case YELLOW_BANNER, YELLOW_WALL_BANNER -> YELLOW;
            case WHITE_BANNER, WHITE_WALL_BANNER -> WHITE;
            default -> null;
        };
    }

    public static void register() {
        @NotNull final Path iconFolder = path.resolve("banners");

        @NotNull final IconRegistry registry = Pl3xMap.api().getIconRegistry();

        for (final Icon icon : values()) {
            String bannerFilename = String.format("icons%s%s.png", File.separator, icon.type);

            File file = iconFolder.resolve(bannerFilename).toFile();

            try {
                registry.register(new IconImage(icon.key, ImageIO.read(file), "png"));
            } catch (final IOException exception) {
                logger.warn("Failed to register icon ({}) {}", icon.type, bannerFilename, exception);
            }
        }
    }
}
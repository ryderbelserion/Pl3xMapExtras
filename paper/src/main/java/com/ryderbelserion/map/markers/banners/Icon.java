package com.ryderbelserion.map.markers.banners;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Locale;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public enum Icon {

    BLACK, BLUE, BROWN, CYAN, GREEN, GREY, LIGHT_BLUE, LIGHT_GREY, LIME, MAGENTA, ORANGE, PINK, PURPLE, RED, YELLOW, WHITE;

    private final String key;
    private final String type;

    Icon() {
        this.type = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_banner", this.type);
    }

    public @NotNull String getKey() {
        return this.key;
    }

    private static @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    public static @NotNull Icon get(@NotNull Material type) {
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
            default -> WHITE;
        };
    }

    public static void register() {
        Path iconFolder = plugin.getDataFolder().toPath().resolve("banners");

        for (Icon icon : values()) {
            String bannerFilename = String.format("icons%s%s.png", File.separator, icon.type);

            File file = iconFolder.resolve(bannerFilename).toFile();

            try {
                Pl3xMap.api().getIconRegistry().register(new IconImage(icon.key, ImageIO.read(file), "png"));
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING,"Failed to register icon (" + icon.type + ") " + bannerFilename, e);
            }
        }
    }
}
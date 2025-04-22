package com.ryderbelserion.map.marker.signs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.registry.IconRegistry;
import org.bukkit.block.Sign;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum Icon {
    ACACIA, BAMBOO, BIRCH, CHERRY, CRIMSON, DARK_OAK, JUNGLE, MANGROVE, OAK, SPRUCE, WARPED;

    private final String name;
    private final String key;

    Icon() {
        this.name = name().toLowerCase(Locale.ROOT);
        this.key = String.format("pl3xmap_%s_sign", this.name);
    }

    public @NotNull final String getKey() {
        return this.key;
    }

    private static @NotNull final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private static @NotNull final Path path = plugin.getDataPath();

    private static @NotNull final ComponentLogger logger = plugin.getComponentLogger();

    private static final Map<String, Icon> BY_NAME = new HashMap<>();
    private static final Map<WoodType, Icon> BY_WOOD = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(icon -> BY_NAME.put(icon.name, icon));
        WoodType.values().forEach(type -> BY_WOOD.computeIfAbsent(type, k -> BY_NAME.get(type.name())));
    }

    public static @Nullable Icon get(@NotNull final Sign sign) {
        if (sign instanceof SignBlock block) {
            return BY_WOOD.get(block.type());
        }

        return BY_WOOD.get(WoodType.OAK);
    }

    public static void register() {
        @NotNull final Path iconFolder = path.resolve("signs");

        @NotNull final IconRegistry registry = Pl3xMap.api().getIconRegistry();

        for (final Icon icon : values()) {
            final String signFilename = String.format("icons%s%s_sign.png", File.separator, icon.name);

            final String tooltipKey = String.format("pl3xmap_%s_sign_tooltip", icon.name);
            final String tooltipFilename = String.format("icons%s%s_tooltip.png", File.separator, icon.name);

            try {
                registry.register(new IconImage(icon.key, ImageIO.read(iconFolder.resolve(signFilename).toFile()), "png"));
                registry.register(new IconImage(tooltipKey, ImageIO.read(iconFolder.resolve(tooltipFilename).toFile()), "png"));
            } catch (IOException exception) {
                logger.warn("Failed to register icon ({}) {}", icon.name, signFilename, exception);
            }
        }
    }
}
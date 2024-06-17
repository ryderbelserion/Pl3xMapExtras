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
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import org.bukkit.block.Sign;
import org.bukkit.craftbukkit.block.CraftBlock;
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

    public @NotNull String getKey() {
        return this.key;
    }

    private static final Map<String, Icon> BY_NAME = new HashMap<>();
    private static final Map<WoodType, Icon> BY_WOOD = new HashMap<>();

    static {
        Arrays.stream(values()).forEach(icon -> BY_NAME.put(icon.name, icon));
        WoodType.values().forEach(type -> BY_WOOD.computeIfAbsent(type, k -> BY_NAME.get(type.name())));
    }

    public static @Nullable Icon get(@NotNull Sign sign) {
        return BY_WOOD.get(((SignBlock) ((CraftBlock) sign.getBlock()).getNMS().getBlock()).type());
    }

    public static void register() {
        Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

        Path iconFolder = plugin.getDataFolder().toPath().resolve("signs");

        for (Icon icon : values()) {
            String signFilename = String.format("icons%s%s_sign.png", File.separator, icon.name);

            String tooltipKey = String.format("pl3xmap_%s_sign_tooltip", icon.name);
            String tooltipFilename = String.format("icons%s%s_tooltip.png", File.separator, icon.name);

            try {
                Pl3xMap.api().getIconRegistry().register(new IconImage(icon.key, ImageIO.read(iconFolder.resolve(signFilename).toFile()), "png"));
                Pl3xMap.api().getIconRegistry().register(new IconImage(tooltipKey, ImageIO.read(iconFolder.resolve(tooltipFilename).toFile()), "png"));
            } catch (IOException e) {
                plugin.getLogger().log(Level.WARNING, "Failed to register icon (" + icon.name + ") " + signFilename, e);
            }
        }
    }
}

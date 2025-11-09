package com.ryderbelserion.map.modules.mobs.objects;

import com.ryderbelserion.map.modules.mobs.interfaces.IMobTexture;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import net.pl3x.map.core.registry.IconRegistry;
import org.jetbrains.annotations.NotNull;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;

public class MobTexture extends IMobTexture {

    private String type;
    private String key;

    public MobTexture(@NotNull final Path path) {
        super(path);

        init();
    }

    @Override
    public void init() {
        final String extension = ".png";
        final int depth = 1;

        final Pl3xMap api = Pl3xMap.api();

        final IconRegistry iconRegistry = api.getIconRegistry();

        final String mobName = this.path.getFileName().toString().replace(extension, "");

        for (final Path path : this.fusion.getFiles(this.typesDirectory, extension, depth)) {
            final String type = path.getFileName().toString().replace(extension, "");

            // pl3xmapextras_cow_brown_mob
            final String key = "pl3xmapextras_%s_%s_mob".formatted(mobName, type);

            try {
                final IconImage iconImage = new IconImage(
                        key,
                        ImageIO.read(path.toFile()),
                        "png"
                );

                iconRegistry.register(iconImage);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }

            this.colors.putIfAbsent(type, path);
        }

        for (final Path path : this.fusion.getFiles(this.statesDirectory, extension, depth)) {
            final String type = path.getFileName().toString().replace(extension, "");

            // pl3xmapextras_cow_warm_mob
            final String key = "pl3xmapextras_%s_%s_mob".formatted(mobName, type);

            try {
                final IconImage iconImage = new IconImage(
                        key,
                        ImageIO.read(path.toFile()),
                        "png"
                );

                iconRegistry.register(iconImage);
            } catch (final IOException exception) {
                exception.printStackTrace();
            }

            this.states.putIfAbsent(type, path);
        }

        final Path defaultTexture = this.path.resolve("default.png");

        // pl3xmapextras_cow_default_mob
        final String key = "pl3xmapextras_%s_default_mob".formatted(mobName);

        this.type = mobName;
        this.key = key;

        try {
            final IconImage iconImage = new IconImage(
                    key,
                    ImageIO.read(defaultTexture.toFile()),
                    "png"
            );

            iconRegistry.register(iconImage);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }

    public @NotNull final String getKey() {
        return this.key;
    }

    public @NotNull final String getType() {
        return this.type;
    }
}
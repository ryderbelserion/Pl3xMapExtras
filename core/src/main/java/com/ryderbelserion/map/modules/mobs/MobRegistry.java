package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.modules.mobs.interfaces.IMobTexture;
import com.ryderbelserion.map.modules.mobs.objects.MobTexture;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MobRegistry {

    private final Map<String, IMobTexture> textures = new HashMap<>();

    private final FusionCore fusion = FusionProvider.getInstance();

    private final Pl3xMapCommon plugin;
    private final Path path;

    public MobRegistry(@NotNull final Pl3xMapCommon plugin) {
        this.path = plugin.getPath();

        this.plugin = plugin;
    }

    public void init() {
        final Path iconDirectory = this.path.resolve("mobs").resolve("icons");

        try (final Stream<Path> output = Files.list(iconDirectory)) {
            for (final Path directory : output.toList()) {
                final String mobName = directory.getFileName().toString();

                this.fusion.log("warn", "Mob Name: {}", mobName);

                this.textures.putIfAbsent(mobName, new MobTexture(directory));
            }
        } catch (final IOException exception) {
            this.fusion.log("warn", "Failed to list {}", iconDirectory);
        }

        new MobListener(this.plugin, this);

        this.fusion.log("info", "The mobs module has been initialized!");
    }

    public @NotNull IMobTexture getTexture(@NotNull final Key key) {
        return getTexture(key.asMinimalString());
    }

    public @NotNull IMobTexture getTexture(@NotNull final String key) {
        return this.textures.get(key);
    }

    public @NotNull final Map<String, IMobTexture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
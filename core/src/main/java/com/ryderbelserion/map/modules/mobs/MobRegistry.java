package com.ryderbelserion.map.modules.mobs;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.enums.constants.Namespaces;
import com.ryderbelserion.map.modules.mobs.interfaces.IMobTexture;
import com.ryderbelserion.map.modules.mobs.objects.Mob;
import com.ryderbelserion.map.modules.mobs.objects.MobTexture;
import com.ryderbelserion.map.objects.MapPosition;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MobRegistry {

    private final Map<String, MobTexture> textures = new HashMap<>();

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

                this.textures.putIfAbsent(mobName, new MobTexture(directory));
            }
        } catch (final IOException exception) {
            this.fusion.log("warn", "Failed to list {}", iconDirectory);
        }

        new MobListener(this.plugin, this);

        this.fusion.log("info", "The mobs module has been initialized!");
    }

    public void displayMob(@NotNull final Component displayName, @NotNull final String entityType, @NotNull final UUID uuid, @NotNull final MapPosition position, @NotNull final Consumer<Mob> consumer) {
        if (!this.textures.containsKey(entityType)) { // no icon found obviously
            this.fusion.log("warn", "Could not find %s in the cache!".formatted(entityType));

            return;
        }

        final String worldName = position.worldName();

        removeMob(worldName, uuid); // remove just in case.

        getLayer(worldName).ifPresentOrElse(layer -> {
            final Mob mob = new Mob(
                    displayName,
                    uuid,
                    position,
                    this.textures.get(entityType)
            );

            consumer.accept(mob);

            layer.displayMob(mob);
        }, () -> this.fusion.log("warn", "Could not add mob with uuid %s(%s) to %s".formatted(uuid, entityType, worldName)));
    }

    public void displayMob(@NotNull final Component displayName, @NotNull final String entityType, @NotNull final UUID uuid, @NotNull final MapPosition position) {
        displayMob(displayName, entityType, uuid, position, consumer -> {});
    }

    public void removeMob(@NotNull final String worldName, @NotNull final UUID uuid) {
        getLayer(worldName).ifPresentOrElse(layer -> layer.removeMob(worldName, uuid), () -> this.fusion.log("warn", "Could not remove mob with uuid %s from %s".formatted(uuid, worldName)));
    }

    public void removeWorld(@NotNull final String worldName) {
        getLayer(worldName).ifPresent(MobLayer::purgeCache);
    }

    public @NotNull final Optional<MobLayer> getLayer(@NotNull final String worldName) {
        final World world = Pl3xMap.api().getWorldRegistry().get(worldName);

        if (world == null || !world.isEnabled()) {
            return Optional.empty();
        }

        return Optional.ofNullable((MobLayer) world.getLayerRegistry().get(Namespaces.mob_key));
    }

    public @NotNull final IMobTexture getTexture(@NotNull final Key key) {
        return getTexture(key.asMinimalString());
    }

    public @NotNull final IMobTexture getTexture(@NotNull final String key) {
        return this.textures.get(key);
    }

    public final boolean hasTexture(@NotNull final String key) {
        return this.textures.containsKey(key);
    }

    public @NotNull final Map<String, IMobTexture> getTextures() {
        return Collections.unmodifiableMap(this.textures);
    }
}
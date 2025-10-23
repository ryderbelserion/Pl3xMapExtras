package com.ryderbelserion.map.banners.config;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.enums.Files;
import com.ryderbelserion.map.objects.MapParticle;
import com.ryderbelserion.map.objects.MapSound;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.util.Optional;

public class BannerConfig {

    private final FusionCore fusion = FusionProvider.getInstance();

    private boolean isBlockInteract;
    private boolean isBlockPlace;
    private boolean isEnabled;

    private MapParticle spawnParticle;
    private MapSound spawnSound;

    private MapParticle removeParticle;
    private MapSound removeSound;

    private LayerConfig layerConfig;
    private IconConfig iconConfig;

    public void init() {
        final CommentedConfigurationNode configuration = Files.banner_config.getYamlConfiguration().node("banner");

        this.isEnabled = configuration.node("enabled").getBoolean(false);

        if (!this.isEnabled) {
            return;
        }

        this.isBlockInteract = configuration.node("block-interact").getBoolean(false);
        this.isBlockPlace = configuration.node("block-place").getBoolean(false);

        Optional.of(configuration.node("layer")).ifPresentOrElse(section -> {
            this.layerConfig = new LayerConfig(section);

            this.fusion.log("warn", "The layer section was found in banners.yml!");
        }, () -> {
            this.layerConfig = new LayerConfig();

            this.fusion.log("warn", "The layer section was not found in banners.yml, Default values are being used!");
        });

        Optional.of(configuration.node("icon")).ifPresentOrElse(section -> {
            this.iconConfig = new IconConfig(section);

            this.fusion.log("warn", "The icon section was found in banners.yml!");
        }, () -> {
            this.iconConfig = new IconConfig();

            this.fusion.log("warn", "The icon section was not found in banners.yml, Default values are being used!");
        });

        Optional.of(configuration.node("particle")).ifPresent(section -> {
            final boolean isEnabled = section.node("enabled").getBoolean(false);

            if (!isEnabled) return;

            final String spawnedParticle = section.node("spawned").getString("happy_villager");

            if (!spawnedParticle.isEmpty()) {
                this.spawnParticle = new MapParticle(spawnedParticle);
            }

            final String removeParticle = section.node("remove").getString("wax_on");

            if (!removeParticle.isEmpty()) {
                this.removeParticle = new MapParticle(removeParticle);
            }
        });

        Optional.of(configuration.node("sound")).ifPresent(section -> {
            final boolean isEnabled = section.node("enabled").getBoolean(false);

            if (!isEnabled) return;

            Optional.of(configuration.node("spawned")).ifPresent(sound -> {
                final String type = sound.node("sound").getString("entity.player.levelup");

                if (type.isEmpty()) return;

                this.spawnSound = new MapSound(type, sound.node("source").getString("master"), sound.node("volume").getDouble(1.0), sound.node("pitch").getDouble(1.0));
            });

            Optional.of(configuration.node("removed")).ifPresent(sound -> {
                final String type = sound.node("sound").getString("entity.ghast.hurt");

                if (type.isEmpty()) return;

                this.removeSound = new MapSound(type, sound.node("source").getString("master"), sound.node("volume").getDouble(1.0), sound.node("pitch").getDouble(1.0));
            });
        });
    }

    public @NotNull final Optional<MapParticle> getRemoveParticle() {
        return Optional.ofNullable(this.removeParticle);
    }

    public @NotNull final Optional<MapSound> getRemoveSound() {
        return Optional.ofNullable(this.removeSound);
    }

    public @NotNull final Optional<MapParticle> getSpawnParticle() {
        return Optional.ofNullable(this.spawnParticle);
    }

    public @NotNull final Optional<MapSound> getSpawnSound() {
        return Optional.ofNullable(this.spawnSound);
    }

    public @NotNull final LayerConfig getLayerConfig() {
        return this.layerConfig;
    }

    public @NotNull final IconConfig getIconConfig() {
        return this.iconConfig;
    }

    public final boolean isBlockInteract() {
        return this.isBlockInteract;
    }

    public final boolean isBlockPlace() {
        return this.isBlockPlace;
    }

    public final boolean isEnabled() {
        return this.isEnabled;
    }
}
package com.ryderbelserion.map.enums;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.fusion.core.files.types.JsonCustomFile;
import com.ryderbelserion.fusion.core.files.types.YamlCustomFile;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;
import java.util.Optional;

public enum Files {

    banner_config("banners/banners.yml"),
    banner_data("storage/banners.json");

    private final FusionCore fusion = FusionProvider.getInstance();

    private final FileManager fileManager = this.fusion.getFileManager();

    private final Path path;

    Files(@NotNull final String path) {
        this.path = this.fusion.getDataPath().resolve(path);
    }

    public @NotNull final BasicConfigurationNode getJsonConfiguration() {
        return getJsonCustomFile().getConfiguration();
    }

    public JsonCustomFile getJsonCustomFile() {
        @NotNull final Optional<JsonCustomFile> customFile = this.fileManager.getJsonFile(this.path);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.path);
        }

        return customFile.get();
    }

    public @NotNull final CommentedConfigurationNode getYamlConfiguration() {
        return getYamlCustomFile().getConfiguration();
    }

    public @NotNull final YamlCustomFile getYamlCustomFile() {
        @NotNull final Optional<YamlCustomFile> customFile = this.fileManager.getYamlFile(this.path);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.path);
        }

        return customFile.get();
    }

    public @NotNull final Path getPath() {
        return this.path;
    }

    public void save() {
        this.fileManager.saveFile(this.path);
    }
}

package com.ryderbelserion.map.common.api;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.files.enums.FileAction;
import com.ryderbelserion.fusion.files.enums.FileType;
import com.ryderbelserion.fusion.files.interfaces.ICustomFile;
import com.ryderbelserion.fusion.files.types.configurate.JsonCustomFile;
import com.ryderbelserion.fusion.files.types.configurate.YamlCustomFile;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.CommentedConfigurationNode;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;

public enum FileKeys {

    messages("messages.yml", FileType.YAML),
    config("config.yml", FileType.YAML),

    banners_storage("banners.json", FileType.JSON, "storage"),
    banners_config("banners.yml", FileType.YAML, "banners");

    private final FusionCore fusion = FusionProvider.getInstance();
    private final FileManager fileManager = this.fusion.getFileManager();

    private final Path path = this.fusion.getDataPath();

    private final FileType fileType;
    private final Path location;

    FileKeys(@NotNull final String fileName, @NotNull final FileType fileType, @NotNull final String module, @NotNull final String folder) {
        this.location = this.path.resolve(module).resolve(folder).resolve(fileName);
        this.fileType = fileType;
    }

    FileKeys(@NotNull final String fileName, @NotNull final FileType fileType, @NotNull final String folder) {
        this.location = this.path.resolve(folder).resolve(fileName);
        this.fileType = fileType;
    }

    FileKeys(@NotNull final String name, @NotNull final FileType fileType) {
        this.location = this.path.resolve(name);
        this.fileType = fileType;
    }

    public @NotNull final BasicConfigurationNode getJsonConfig() {
        return getJsonCustomFile().getConfiguration();
    }

    public JsonCustomFile getJsonCustomFile() {
        @NotNull final Optional<JsonCustomFile> customFile = this.fileManager.getJsonFile(this.location);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.location);
        }

        return customFile.get();
    }

    public @NotNull final CommentedConfigurationNode getYamlConfig() {
        return getYamlCustomFile().getConfiguration();
    }

    public @NotNull final YamlCustomFile getYamlCustomFile() {
        @NotNull final Optional<YamlCustomFile> customFile = this.fileManager.getYamlFile(this.location);

        if (customFile.isEmpty()) {
            throw new FusionException("Could not find custom file for " + this.location);
        }

        return customFile.get();
    }

    public void addFile(@NotNull final Consumer<ICustomFile<?, ?, ?, ?>> consumer) {
        this.fileManager.addFile(this.location, this.fileType, consumer);
    }

    public void addFile() {
        this.fileManager.addFile(this.location, this.fileType);
    }

    public void save() {
        this.fileManager.saveFile(this.path);
    }

    public @NotNull final Path getPath() {
        return this.location;
    }
}
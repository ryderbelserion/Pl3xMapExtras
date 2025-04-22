package com.ryderbelserion.map.config;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.fusion.core.managers.files.FileManager;
import com.ryderbelserion.fusion.core.managers.files.types.JaluCustomFile;
import com.ryderbelserion.map.config.types.ConfigKeys;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public class ConfigManager {

    private final FileManager fileManager;
    private final Path path;

    public ConfigManager(@NotNull final FileManager fileManager, @NotNull final Path path) {
        this.fileManager = fileManager;
        this.path = path;
    }

    public void load() {
        this.fileManager.addFile(this.path.resolve("config.yml"), consumer -> consumer.configurationData(ConfigKeys.class), YamlFileResourceOptions.builder().build(), false, true);
    }

    public void reload() {
        getConfig().load();
    }

    public final SettingsManager getSettings() {
        return getConfig().getConfig();
    }

    public final JaluCustomFile getConfig() {
        return this.fileManager.getJaluFile(this.path.resolve("config.yml"));
    }
}
package com.ryderbelserion.map.config;

import ch.jalu.configme.SettingsManager;
import ch.jalu.configme.resource.YamlFileResourceOptions;
import com.ryderbelserion.fusion.core.managers.files.FileManager;
import com.ryderbelserion.fusion.core.managers.files.types.JaluCustomFile;
import com.ryderbelserion.map.config.types.BannerConfig;
import com.ryderbelserion.map.config.types.ConfigKeys;
import org.jetbrains.annotations.NotNull;
import java.nio.charset.Charset;
import java.nio.file.Path;

public class ConfigManager {

    private final FileManager fileManager;
    private final Path path;

    public ConfigManager(@NotNull final FileManager fileManager, @NotNull final Path path) {
        this.fileManager = fileManager;
        this.path = path;
    }

    private JaluCustomFile config;

    public void load() {
        YamlFileResourceOptions builder = YamlFileResourceOptions.builder().charset(Charset.defaultCharset()).indentationSize(2).build();

        this.fileManager.addFile(this.path.resolve("config.yml"), consumer -> {
            consumer.configurationData(ConfigKeys.class);
        }, builder, false, true);

        this.fileManager.addFile(this.path.resolve("banners").resolve("banners.yml"), consumer -> {
            consumer.configurationData(BannerConfig.class);
        }, builder, false, true);

        this.config = this.fileManager.getJaluFile(this.path.resolve("config.yml"));
    }

    public void reload() {
        this.config.load();
    }

    public SettingsManager getConfig() {
        return this.config.getConfig();
    }
}
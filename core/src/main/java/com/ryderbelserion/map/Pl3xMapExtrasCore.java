package com.ryderbelserion.map;

import com.ryderbelserion.fusion.core.managers.files.FileManager;
import com.ryderbelserion.map.config.ConfigManager;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public class Pl3xMapExtrasCore {

    private final ConfigManager configManager;
    private final FileManager fileManager;
    private final ComponentLogger logger;
    private final Path path;

    public Pl3xMapExtrasCore(@NotNull final FileManager fileManager, @NotNull final ComponentLogger logger, @NotNull final Path path) {
        this.fileManager = fileManager;
        this.logger = logger;
        this.path = path;

        this.configManager = new ConfigManager(this.fileManager, path);
        this.configManager.load();
    }

    public @NotNull final ConfigManager getConfigManager() {
        return configManager;
    }

    public @NotNull final FileManager getFileManager() {
        return this.fileManager;
    }

    public @NotNull final ComponentLogger getLogger() {
        return this.logger;
    }

    public @NotNull final Path getPath() {
        return this.path;
    }
}
package com.ryderbelserion.map;

import java.nio.file.Path;
import java.io.File;
import java.util.logging.Logger;

public class Provider {

    private static MapExtras instance;

    public static MapExtras getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Pl3xMapExtras did not enable properly.");
        }

        return instance;
    }

    private Provider() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

    public static void register(MapExtras instance) {
        if (Provider.instance != null) return;

        Provider.instance = instance;
    }

    public static void unregister() {
        Provider.instance = null;
    }

    public static class MapExtras {

        private final Path dataFolder;
        private final Logger logger;

        public MapExtras(File dataFolder, Logger logger) {
            this.dataFolder = dataFolder.toPath();
            this.logger = logger;
        }

        public Path getDataFolder() {
            return this.dataFolder;
        }

        public Logger getLogger() {
            return this.logger;
        }
    }
}
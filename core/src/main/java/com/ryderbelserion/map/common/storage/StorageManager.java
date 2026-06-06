package com.ryderbelserion.map.common.storage;

import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.storage.IStorageHolder;
import com.ryderbelserion.map.common.storage.impl.types.ConnectionStorage;
import com.ryderbelserion.map.common.storage.impl.types.file.types.SqliteFactory;
import org.jspecify.annotations.NonNull;
import java.nio.file.Path;

public class StorageManager {

    private final Path dataPath;

    public StorageManager(@NonNull final Pl3xMapPlugin plugin) {
        this.dataPath = plugin.getDataPath();
    }

    public IStorageHolder init() {
        final String type = "SQLITE".toLowerCase();

        return switch (type) {
            case "sqlite" -> new ConnectionStorage(new SqliteFactory(this.dataPath.resolve("pl3xmap.db"))).init();

            default -> throw new FusionException("Unknown Database Type: %s".formatted(type));
        };
    }
}
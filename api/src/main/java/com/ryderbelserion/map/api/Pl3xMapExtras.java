package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.files.FileManager;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.api.adapters.IPlayerAdapter;
import com.ryderbelserion.map.api.registry.IContextRegistry;
import com.ryderbelserion.map.api.registry.IMessageRegistry;
import com.ryderbelserion.map.api.registry.IUserRegistry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public abstract class Pl3xMapExtras {

    public static final String namespace = "pl3xmapextras";

    protected final FileManager fileManager;
    protected final FusionKyori fusion;
    protected final Path dataPath;

    public Pl3xMapExtras(@NotNull final FusionKyori fusion) {
        this.fileManager = fusion.getFileManager();
        this.dataPath = fusion.getDataPath();
        this.fusion = fusion;
    }

    public abstract <C> @NotNull IPlayerAdapter<C> getPlayerAdapter(@NotNull final Class<C> object);

    public abstract IMessageRegistry getMessageRegistry();

    public abstract IContextRegistry getContextRegistry();

    public abstract IUserRegistry getUserRegistry();

    public abstract void init();

    public abstract void reload();

    public abstract void post();

    public void shutdown() {

    }

    public final FileManager getFileManager() {
        return this.fileManager;
    }

    public final Path getStoragePath() {
        return this.dataPath.resolve("storage");
    }

    public final Path getDataPath() {
        return this.dataPath;
    }

    public FusionKyori getFusion() {
        return this.fusion;
    }

    public static class Provider {

        private static Pl3xMapExtras instance;

        @ApiStatus.Internal
        private Provider() {
            throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
        }

        public static Pl3xMapExtras getInstance() {
            return instance;
        }

        @ApiStatus.Internal
        public static void register(@NotNull final Pl3xMapExtras instance) {
            Provider.instance = instance;
        }

        @ApiStatus.Internal
        public static void unregister() {
            Provider.instance = null;
        }
    }
}
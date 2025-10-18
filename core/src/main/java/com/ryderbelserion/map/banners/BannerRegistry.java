package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.files.FileManager;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.banners.objects.BannerTexture;
import net.pl3x.map.core.Pl3xMap;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class BannerRegistry {

    private final Map<String, BannerTexture> textures = new HashMap<>();

    private final FileManager fileManager;
    private final Pl3xMapCommon plugin;
    private final FusionCore fusion;
    private final Path path;

    public BannerRegistry(@NotNull final Pl3xMapCommon plugin) {
        this.fileManager = plugin.getFileManager();
        this.fusion = plugin.getFusion();
        this.path = plugin.getPath();

        this.plugin = plugin;
    }

    public void init() {
        this.fileManager.extractFolder("banners", this.path);

        new BannerListener(this.plugin);

        this.fusion.log("info", "The banner module has been initialized!");
    }

    public @NotNull final BannerTexture getTexture(@NotNull final String fileName) {
        return this.textures.get(fileName);
    }
}
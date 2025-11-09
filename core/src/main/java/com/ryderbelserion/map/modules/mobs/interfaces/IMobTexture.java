package com.ryderbelserion.map.modules.mobs.interfaces;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class IMobTexture {

    protected final FusionCore fusion = FusionProvider.getInstance();

    protected final Map<String, Path> states = new HashMap<>(); // stores states, and the path leading to the icon.

    protected final Map<String, Path> colors = new HashMap<>(); // stores colors, and the path leading to the icon.

    protected final Path path;
    protected final Path statesDirectory;
    protected final Path typesDirectory;

    public IMobTexture(@NotNull final Path path) {
        this.path = path; // the directory holding the mob data
        this.statesDirectory = this.path.resolve("states");
        this.typesDirectory = this.path.resolve("types");
    }

    public abstract void init();
}
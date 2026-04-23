package com.ryderbelserion.map.common.modules.banners.objects;

import com.ryderbelserion.fusion.core.api.FusionProvider;
import com.ryderbelserion.fusion.kyori.FusionKyori;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.image.IconImage;
import org.jetbrains.annotations.NotNull;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Path;

public class BannerTexture {

    private final FusionKyori fusion = (FusionKyori) FusionProvider.getInstance();

    private final String type;
    private final String key;
    private final Path path;

    public BannerTexture(@NotNull final Path path, @NotNull final String fileName) {
        this.key = "pl3xmapextras_%s_banner".formatted(this.type = fileName);
        this.path = path;
    }

    public static BannerTexture of(@NotNull final Path path, @NotNull final String fileName) {
        return new BannerTexture(path, fileName);
    }

    public void register() {
        try {
            Pl3xMap.api().getIconRegistry().register(new IconImage(
                    this.key,
                    ImageIO.read(this.path.toFile()),
                    "png"
            ));
        } catch (final IOException exception) {
            this.fusion.log("warn", "Failed to register icon (%s), %s".formatted(this.key, this.path), exception);
        }
    }

    public @NotNull final String getType() {
        return this.type;
    }

    public @NotNull final String getKey() {
        return this.key;
    }

    public @NotNull final Path getPath() {
        return this.path;
    }
}
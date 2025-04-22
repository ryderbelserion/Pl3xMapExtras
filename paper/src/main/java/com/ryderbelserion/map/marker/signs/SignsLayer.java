package com.ryderbelserion.map.marker.signs;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import com.ryderbelserion.map.config.v1.SignsConfig;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import org.jetbrains.annotations.NotNull;

public class SignsLayer extends WorldLayer {

    public static final String KEY = "pl3xmap_signs";

    private final Path dataFile;
    private final SignsConfig config;
    private final Options options;

    private final Map<Position, Sign> signs = new ConcurrentHashMap<>();

    public SignsLayer(@NotNull final SignsConfig config) {
        super(KEY, config.getWorld(), () -> config.LAYER_LABEL);

        this.config = config;
        this.dataFile = getWorld().getTilesDirectory().resolve("signs.dat");

        setShowControls(config.LAYER_SHOW_CONTROLS);
        setDefaultHidden(config.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(config.LAYER_UPDATE_INTERVAL);
        setPriority(config.LAYER_PRIORITY);
        setZIndex(config.LAYER_ZINDEX);
        setCss(config.LAYER_CSS);
        this.options = new Options.Builder()
                .popupOffset(Point.of(0, 10))
                .popupMaxWidth(196)
                .popupMinWidth(196)
                .popupMaxHeight(210)
                .popupCloseButton(false)
                .build();

        loadData();
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.signs.values().stream().map(sign -> {
            final String key = String.format("%s_%s_%d_%d", KEY, getWorld().getName(), sign.pos().x(), sign.pos().z());

            return Marker.icon(key, sign.pos().toPoint(), sign.icon().getKey(), getConfig().ICON_SIZE)
                    .setOptions(this.options.asBuilder()
                            .popupPane(String.format("%s_popup", sign.icon().getKey()))
                            .popupContent(getConfig().ICON_POPUP_CONTENT
                                    .replace("<line1>", sign.lines().get(0))
                                    .replace("<line2>", sign.lines().get(1))
                                    .replace("<line3>", sign.lines().get(2))
                                    .replace("<line4>", sign.lines().get(3))
                            ).build());
        }).collect(Collectors.toList());
    }

    public @NotNull SignsConfig getConfig() {
        return this.config;
    }

    public @NotNull Collection<Sign> getSigns() {
        return Collections.unmodifiableCollection(this.signs.values());
    }

    public boolean hasSign(@NotNull final Position pos) {
        return this.signs.containsKey(pos);
    }

    public void putSign(@NotNull final Sign sign) {
        this.signs.put(sign.pos(), sign);

        saveData();
    }

    public void removeSign(@NotNull final Position pos) {
        this.signs.remove(pos);
        saveData();
    }

    private void loadData() {
        if (!Files.exists(this.dataFile)) {
            return;
        }

        try (DataInputStream in = new DataInputStream(new GZIPInputStream(new FileInputStream(this.dataFile.toFile())))) {
            int size = in.readInt();

            for (int i = 0; i < size; i++) {
                final Sign sign = Sign.load(in);

                this.signs.put(sign.pos(), sign);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private void saveData() {
        try (DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(this.dataFile.toFile())))) {
            out.writeInt(this.signs.size());

            for (final Sign sign : this.signs.values()) {
                sign.save(out);
            }

            out.flush();
        } catch (Throwable ignore) {}
    }
}
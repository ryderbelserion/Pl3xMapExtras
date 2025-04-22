package com.ryderbelserion.map.marker.banners;

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
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import com.ryderbelserion.map.config.BannerConfig;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import org.jetbrains.annotations.NotNull;

public class BannersLayer extends WorldLayer {

    public static final String KEY = "pl3xmap_banners";

    private final Path dataFile;
    private final BannerConfig config;

    private final Map<Position, Marker<?>> markers = new ConcurrentHashMap<>();
    private final Map<Position, Banner> banners = new ConcurrentHashMap<>();

    public BannersLayer(@NotNull final BannerConfig config) {
        super(KEY, config.getWorld(), () -> config.LAYER_LABEL);

        this.config = config;
        this.dataFile = getWorld().getTilesDirectory().resolve("banners.dat");

        setShowControls(config.LAYER_SHOW_CONTROLS);
        setDefaultHidden(config.LAYER_DEFAULT_HIDDEN);
        setUpdateInterval(config.LAYER_UPDATE_INTERVAL);
        setPriority(config.LAYER_PRIORITY);
        setZIndex(config.LAYER_ZINDEX);
        setCss(config.LAYER_CSS);

        loadData();
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.markers.values();
    }

    public @NotNull Collection<Banner> getBanners() {
        return Collections.unmodifiableCollection(this.banners.values());
    }

    public void putBanner(@NotNull final Banner banner) {
        putBanner(banner, true);
    }

    public void putBanner(@NotNull final Banner banner, final boolean saveData) {
        String key = String.format("%s_%s_%d_%d", KEY, getWorld().getName(), banner.pos().x(), banner.pos().z());

        Icon icon = Marker.icon(key, banner.pos().toPoint(), banner.icon().getKey(), this.config.ICON_SIZE)
                .setAnchor(this.config.ICON_ANCHOR)
                .setRotationAngle(this.config.ICON_ROTATION_ANGLE)
                .setRotationOrigin(this.config.ICON_ROTATION_ORIGIN)
                .setShadow(this.config.ICON_SHADOW)
                .setShadowSize(this.config.ICON_SHADOW_SIZE)
                .setShadowAnchor(this.config.ICON_SHADOW_ANCHOR);

        if (banner.name() != null && !banner.name().isBlank()) {
            Options.Builder builder = new Options.Builder();

            if (this.config.ICON_TOOLTIP_CONTENT != null) {
                builder.tooltipContent(this.config.ICON_TOOLTIP_CONTENT.replace("<name>", banner.name()))
                        .tooltipPane(this.config.ICON_TOOLTIP_PANE)
                        .tooltipOffset(this.config.ICON_TOOLTIP_OFFSET)
                        .tooltipDirection(this.config.ICON_TOOLTIP_DIRECTION)
                        .tooltipPermanent(this.config.ICON_TOOLTIP_PERMANENT)
                        .tooltipSticky(this.config.ICON_TOOLTIP_STICKY)
                        .tooltipOpacity(this.config.ICON_TOOLTIP_OPACITY);
            }

            if (this.config.ICON_POPUP_CONTENT != null) {
                builder.popupContent(this.config.ICON_POPUP_CONTENT.replace("<name>", banner.name()))
                        .popupPane(this.config.ICON_POPUP_PANE)
                        .popupOffset(this.config.ICON_POPUP_OFFSET)
                        .popupMaxWidth(this.config.ICON_POPUP_MAX_WIDTH)
                        .popupMinWidth(this.config.ICON_POPUP_MIN_WIDTH)
                        .popupMaxHeight(this.config.ICON_POPUP_MAX_HEIGHT)
                        .popupShouldAutoPan(this.config.ICON_POPUP_SHOULD_AUTO_PAN)
                        .popupAutoPanPaddingTopLeft(this.config.ICON_POPUP_AUTO_PAN_PADDING_TOP_LEFT)
                        .popupAutoPanPaddingBottomRight(this.config.ICON_POPUP_AUTO_PAN_PADDING_BOTTOM_RIGHT)
                        .popupAutoPanPadding(this.config.ICON_POPUP_AUTO_PAN_PADDING)
                        .popupShouldKeepInView(this.config.ICON_POPUP_SHOULD_KEEP_IN_VIEW)
                        .popupCloseButton(this.config.ICON_POPUP_CLOSE_BUTTON)
                        .popupShouldAutoClose(this.config.ICON_POPUP_SHOULD_AUTO_CLOSE)
                        .popupShouldCloseOnEscapeKey(this.config.ICON_POPUP_SHOULD_CLOSE_ON_ESCAPE_KEY)
                        .popupShouldCloseOnClick(this.config.ICON_POPUP_SHOULD_CLOSE_ON_CLICK);
            }

            icon.setOptions(builder.build());
        }

        this.markers.put(banner.pos(), icon);
        this.banners.put(banner.pos(), banner);

        if (saveData) {
            saveData();
        }
    }

    public void removeBanner(@NotNull final Position pos) {
        this.markers.remove(pos);
        this.banners.remove(pos);

        saveData();
    }

    public boolean containsBanner(@NotNull final Position pos) {
        return this.markers.containsKey(pos) && this.banners.containsKey(pos);
    }

    private void loadData() {
        if (!Files.exists(this.dataFile)) {
            return;
        }

        try (DataInputStream in = new DataInputStream(new GZIPInputStream(new FileInputStream(this.dataFile.toFile())))) {
            int size = in.readInt();

            for (int i = 0; i < size; i++) {
                putBanner(Banner.load(in), false);
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private void saveData() {
        try (DataOutputStream out = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(this.dataFile.toFile())))) {
            Collection<Banner> banners = getBanners();
            out.writeInt(banners.size());

            for (Banner banner : banners) {
                banner.save(out);
            }

            out.flush();
        } catch (Throwable ignore) {}
    }
}
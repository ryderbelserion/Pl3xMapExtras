package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.banners.config.BannerConfig;
import com.ryderbelserion.map.banners.config.IconConfig;
import com.ryderbelserion.map.configs.LayerConfig;
import com.ryderbelserion.map.banners.interfaces.IBannerLayer;
import com.ryderbelserion.map.banners.objects.Banner;
import com.ryderbelserion.map.banners.objects.BannerTexture;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.enums.Files;
import com.ryderbelserion.map.objects.MapPosition;
import com.ryderbelserion.map.utils.ConfigUtils;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.Vector;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BannerLayer extends WorldLayer implements IBannerLayer {

    private final FusionCore fusion = FusionProvider.getInstance();

    private final Pl3xMapCommon plugin;

    private final Map<MapPosition, Marker<?>> markers = new ConcurrentHashMap<>();
    private final Map<MapPosition, Banner> banners = new ConcurrentHashMap<>();

    public BannerLayer(@NotNull final Pl3xMapCommon plugin, @NotNull final BannerRegistry registry, @NotNull final World world) {
        super(Namespaces.banner_key, world, () -> plugin.getBannerConfig().getLayerConfig().getLayerLabel());

        this.plugin = plugin;

        final LayerConfig config = this.plugin.getBannerConfig().getLayerConfig();

        setShowControls(config.isShowControls());
        setDefaultHidden(config.isHiddenByDefault());
        setPriority(config.getPriority());
        setZIndex(config.getIndex());
        setCss(config.getCss());

        final int interval = config.getUpdateInterval();

        if (interval == -1) {
            setLiveUpdate(true);
        } else {
            setUpdateInterval(interval);
        }

        init(registry, world);

        this.fusion.log("warn", "The banner layer for {} is ready!", world.getName());
    }

    public void init(@NotNull final BannerRegistry registry, @NotNull final World world) {
        final String worldName = world.getName();

        final BasicConfigurationNode root = node(worldName, "");

        for (final Map.Entry<Object, BasicConfigurationNode> child : root.childrenMap().entrySet()) {
            final List<String> locations = ConfigUtils.getStringList(child.getValue());

            final String name = child.getKey().toString();

            for (final String location : locations) {
                final String[] splitter = location.split(",");

                displayBanner(new Banner(
                        registry.getTexture(name),
                        splitter[3],
                        worldName,
                        new MapPosition(
                                Integer.parseInt(splitter[0]),
                                Integer.parseInt(splitter[1]),
                                Integer.parseInt(splitter[2])
                        )
                ), false);
            }
        }
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.markers.values();
    }

    public @NotNull final Collection<Banner> getBanners() {
        return Collections.unmodifiableCollection(this.banners.values());
    }

    @Override
    public void displayBanner(@NotNull final Banner banner, final boolean cacheLookUp) {
        final String name = banner.bannerName();

        final MapPosition position = banner.position();

        final int x = position.x();
        final int y = position.y();
        final int z = position.z();

        final BannerTexture texture = banner.texture();

        final String type = texture.getType();
        final String key = texture.getKey();
        final Path path = texture.getPath();

        final String format = "%s_%s_%d_%d".formatted(Namespaces.banner_key, banner.worldName(), x, z);

        final BannerConfig config = this.plugin.getBannerConfig();

        final IconConfig iconConfig = config.getIconConfig();

        Icon icon = Marker.icon(format, position.asPoint(), key, iconConfig.getIconSize());

        final Vector anchorVector = iconConfig.getAnchorSize();

        icon.setAnchor(anchorVector.x() > 0 && anchorVector.z() > 0 ? anchorVector : null);

        icon.setRotationAngle(iconConfig.getRotationAngle() > 0.0 ? iconConfig.getRotationAngle() : null);

        icon.setRotationOrigin(!iconConfig.getRotationOrigin().isEmpty() ? iconConfig.getRotationOrigin() : null);

        icon.setShadow(!iconConfig.getShadowImage().isEmpty() ? iconConfig.getShadowImage() : null);

        final Vector shadowSize = iconConfig.getShadowSize();

        icon.setShadowSize(shadowSize.x() > 0 && shadowSize.z() > 0 ? shadowSize : null);

        final Vector shadowAnchor = iconConfig.getShadowAnchorSize();

        icon.setShadowAnchor(shadowAnchor.x() > 0 && shadowAnchor.z() > 0 ? shadowAnchor : null);

        if (!name.isEmpty()) {
            Options.Builder builder = new Options.Builder();

            builder.tooltipContent("<center>%s</center>".formatted(name))
                    .tooltipPane(null)
                    .tooltipOffset(new Point(0, -6))
                    .tooltipDirection(Tooltip.Direction.TOP)
                    .tooltipPermanent(null)
                    .tooltipSticky(null)
                    .tooltipOpacity(null);

            /*builder.popupContent(this.config.ICON_POPUP_CONTENT.replace("<name>", banner.name()))
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
                    .popupShouldCloseOnClick(this.config.ICON_POPUP_SHOULD_CLOSE_ON_CLICK);*/

            icon.setOptions(builder.build());
        }

        this.markers.put(position, icon);
        this.banners.put(position, banner);

        final String point = "%s,%s,%s,%s".formatted(x, y, z, name);

        if (cacheLookUp) {
            final String worldName = banner.worldName();

            try {
                final BasicConfigurationNode root = node(worldName, type);

                final List<String> locations = ConfigUtils.getStringList(root);

                if (locations.contains(point)) {
                    this.fusion.log("warn", "Cannot add %s as it already exists in `banners.json`".formatted(point));

                    return;
                }

                root.appendListNode().set(point);

                Files.banner_data.save();
            } catch (SerializationException exception) {
                this.fusion.log("warn", "Failed to serialize and save %s for %s".formatted(point, worldName));
            }
        }

        this.fusion.log("warn", "Successfully added %s to banners.json, and the live view!".formatted(point));
    }

    @Override
    public void removeBanner(@NotNull final Banner banner, final boolean cacheLookUp) {
        final MapPosition position = banner.position();

        final int x = position.x();
        final int y = position.y();
        final int z = position.z();

        final String displayName = banner.bannerName();

        final String point = "%s,%s,%s,%s".formatted(x, y, z, displayName);

        if (!this.markers.containsKey(position)) {
            this.fusion.log("warn", "The cache does not contain (%s)".formatted(point));

            return;
        }

        this.markers.remove(position);
        this.banners.remove(position);

        final String worldName = banner.worldName();

        final BannerTexture texture = banner.texture();

        final String bannerType = texture.getType();

        try {
            final BasicConfigurationNode root = node(worldName, bannerType);

            final List<String> locations = ConfigUtils.getStringList(root);

            if (locations.remove(point)) {
                this.fusion.log("warn", "Successfully removed %s from banners.json".formatted(point));

                root.setList(String.class, locations);

                Files.banner_data.save();
            }
        } catch (final Exception exception) {
            this.fusion.log("warn", "Failed to remove %s for %s".formatted(point, worldName), exception);
        }
    }

    public BasicConfigurationNode node(@NotNull final String worldName, @NotNull final String bannerType) {
        final BasicConfigurationNode root = Files.banner_data.getJsonConfiguration().node("banners", worldName);

        if (bannerType.isEmpty()) {
            return root;
        }

        return root.node(bannerType);
    }
}
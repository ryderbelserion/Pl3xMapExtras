package com.ryderbelserion.map.common.modules.banners;

import com.ryderbelserion.fusion.kyori.FusionKyori;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.Pl3xMapExtras;
import com.ryderbelserion.map.api.constants.Namespaces;
import com.ryderbelserion.map.common.api.FileKeys;
import com.ryderbelserion.map.common.configs.types.map.LayerConfig;
import com.ryderbelserion.map.common.modules.banners.config.BannerConfig;
import com.ryderbelserion.map.common.modules.banners.config.icons.IconConfig;
import com.ryderbelserion.map.common.modules.banners.config.icons.types.PopupConfig;
import com.ryderbelserion.map.common.modules.banners.config.icons.types.TooltipConfig;
import com.ryderbelserion.map.common.modules.banners.interfaces.IBannerLayer;
import com.ryderbelserion.map.common.modules.banners.objects.Banner;
import com.ryderbelserion.map.common.modules.banners.objects.BannerTexture;
import com.ryderbelserion.map.common.objects.MapPosition;
import net.pl3x.map.core.markers.layer.WorldLayer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BannerLayer extends WorldLayer implements IBannerLayer {

    private final Map<MapPosition, Marker<?>> markers = new ConcurrentHashMap<>();
    private final Map<MapPosition, Banner> banners = new ConcurrentHashMap<>();

    private final Pl3xMapPlugin plugin = (Pl3xMapPlugin) Pl3xMapExtras.Provider.getInstance();
    private final BannerRegistry registry = this.plugin.getBannerRegistry();
    private final FusionKyori fusion = this.plugin.getFusion();

    public BannerLayer(@NotNull final BannerConfig config, @NotNull final World world) {
        super(Namespaces.banner_key, world, () -> config.getLayerConfig().getLayerLabel());

        refresh();

        init(world);

        this.fusion.log("warn", "The banner layer for {} is ready!", world.getName());
    }

    public void init(@NotNull final World world) {
        final String worldName = world.getName();

        final BasicConfigurationNode root = node(worldName, "");

        for (final Map.Entry<Object, BasicConfigurationNode> child : root.childrenMap().entrySet()) {
            final List<String> locations = com.ryderbelserion.map.api.utils.ConfigUtils.getStringList(child.getValue());

            final String name = child.getKey().toString();

            for (final String location : locations) {
                final String[] splitter = location.split(",");

                displayBanner(new Banner(
                        this.registry.getTexture(name),
                        splitter[3],
                        new MapPosition(
                                worldName,
                                Integer.parseInt(splitter[0]),
                                Integer.parseInt(splitter[1]),
                                Integer.parseInt(splitter[2])
                        )
                ), false);
            }
        }
    }

    @Override
    public void refresh() {
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

        final String format = "%s_%s_%d_%d".formatted(Namespaces.banner_key, position.worldName(), x, z);

        final BannerConfig config = this.plugin.getBannerConfig();

        final IconConfig iconConfig = config.getIconConfig();

        final Icon icon = Marker.icon(format, position.asPoint(), key, iconConfig.asIconVector());

        icon.setAnchor(iconConfig.asAnchorVector())
                .setRotationAngle(iconConfig.getRotationAngle())
                .setRotationOrigin(iconConfig.getRotationOrigin())
                .setShadow(iconConfig.asShadowVectorImage())
                .setShadowSize(iconConfig.asShadowVectorSize())
                .setShadowAnchor(iconConfig.asShadowAnchorVector());

        if (!name.isEmpty()) {
            Options.Builder builder = new Options.Builder();

            final TooltipConfig tooltipConfig = iconConfig.asToolTip();

            final String content = tooltipConfig.asContent();

            if (content != null) {
                builder.tooltipContent(content.replace("<name>", name))
                        .tooltipPane(tooltipConfig.asPane())
                        .tooltipOffset(tooltipConfig.asPoint())
                        .tooltipDirection(tooltipConfig.asDirection())
                        .tooltipPermanent(tooltipConfig.isPermanent())
                        .tooltipSticky(tooltipConfig.isSticky())
                        .tooltipOpacity(tooltipConfig.getOpacity());
            }
            
            final PopupConfig popup = iconConfig.asPopup();

            final String popupContent = popup.asContent();

            if (popupContent != null) {
                builder.popupContent(popupContent).popupPane(popup.asPane())
                        .popupOffset(popup.asOffset())
                        .popupMaxWidth(popup.getMaxWidth())
                        .popupMinWidth(popup.getMinWidth())
                        .popupMaxHeight(popup.getMaxHeight())
                        .popupShouldAutoPan(popup.isAutoPan())
                        .popupShouldAutoClose(popup.isAutoClose())
                        .popupCloseButton(popup.hasCloseButton())
                        .popupShouldCloseOnEscapeKey(popup.isCloseOnEscape())
                        .popupShouldCloseOnClick(popup.isCloseOnClick())
                        .popupShouldKeepInView(popup.isKeptInView())
                        .popupAutoPanPaddingTopLeft(popup.asLeftPoint())
                        .popupAutoPanPaddingBottomRight(popup.asRightPoint());
            }

            icon.setOptions(builder.build());
        }

        this.markers.put(position, icon);
        this.banners.put(position, banner);

        final String point = "%s,%s,%s,%s".formatted(x, y, z, name);

        if (cacheLookUp) {
            final String worldName = position.worldName();

            try {
                final BasicConfigurationNode root = node(worldName, type);

                final List<String> locations = com.ryderbelserion.map.api.utils.ConfigUtils.getStringList(root);

                if (locations.contains(point)) {
                    this.fusion.log("warn", "Cannot add %s as it already exists in `banners.json`".formatted(point));

                    return;
                }

                root.appendListNode().set(point);

                FileKeys.banners_storage.save();
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

        final String worldName = position.worldName();

        final BannerTexture texture = banner.texture();

        final String bannerType = texture.getType();

        try {
            final BasicConfigurationNode root = node(worldName, bannerType);

            final List<String> locations = com.ryderbelserion.map.api.utils.ConfigUtils.getStringList(root);

            if (locations.remove(point)) {
                this.fusion.log("warn", "Successfully removed %s from banners.json".formatted(point));

                root.setList(String.class, locations);

                FileKeys.banners_storage.save();
            }
        } catch (final Exception exception) {
            this.fusion.log("warn", "Failed to remove %s for %s".formatted(point, worldName), exception);
        }
    }

    public BasicConfigurationNode node(@NotNull final String worldName, @NotNull final String bannerType) {
        final BasicConfigurationNode root = FileKeys.banners_storage.getJsonConfig().node("banners", worldName);

        if (bannerType.isEmpty()) {
            return root;
        }

        return root.node(bannerType);
    }
}
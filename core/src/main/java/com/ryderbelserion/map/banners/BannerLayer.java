package com.ryderbelserion.map.banners;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.fusion.core.FusionProvider;
import com.ryderbelserion.map.banners.objects.Banner;
import com.ryderbelserion.map.banners.objects.BannerTexture;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.enums.Files;
import com.ryderbelserion.map.objects.Position;
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
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BannerLayer extends WorldLayer {

    private final FusionCore fusion = FusionProvider.getInstance();

    private final Map<Position, Marker<?>> markers = new ConcurrentHashMap<>();

    public BannerLayer(@NotNull final BannerRegistry registry, @NotNull final World world) {
        super(Namespaces.banner_key, world, () -> "Banners");

        setShowControls(true);
        setDefaultHidden(false);
        setUpdateInterval(5);
        setPriority(99);
        setZIndex(99);
        setCss("");

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

                final Position position = new Position(Integer.parseInt(splitter[0]), Integer.parseInt(splitter[1]), Integer.parseInt(splitter[2]));

                final Banner banner = new Banner(
                        registry.getTexture(name),
                        position,
                        worldName,
                        splitter[3]
                );

                displayBanner(banner, false);
            }
        }
    }

    @Override
    public @NotNull Collection<Marker<?>> getMarkers() {
        return this.markers.values();
    }

    public void displayBanner(@NotNull final Banner banner, final boolean performConfigLookUp) {
        final String name = banner.getName();

        final Position position = banner.getPosition();

        final int x = position.x();
        final int y = position.y();
        final int z = position.z();

        final BannerTexture texture = banner.getTexture();

        final String type = texture.getType();
        final String key = texture.getKey();
        final Path path = texture.getPath();

        final String format = "%s_%s_%d_%d".formatted(Namespaces.banner_key, banner.getWorldName(), x, z);

        Icon icon = Marker.icon(format, position.asPoint(), key, new Vector(32, 32))
                .setAnchor(null)
                .setRotationAngle(null)
                .setRotationOrigin(null)
                .setShadow(null)
                .setShadowSize(new Vector(20, 20))
                .setShadowAnchor(null);

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

        final String point = "%s,%s,%s,%s".formatted(x, y, z, name);

        if (performConfigLookUp) {
            final String worldName = banner.getWorldName();

            try {
                final BasicConfigurationNode root = node(worldName, type);

                final List<String> locations = ConfigUtils.getStringList(root);

                if (locations.contains(point)) {
                    this.fusion.log("warn", "Cannot add %s as it already exists in `banners.json`".formatted(point));

                    return;
                }

                root.appendListNode().set(point);

                Files.banners.save();
            } catch (SerializationException exception) {
                this.fusion.log("warn", "Failed to serialize and save %s for %s".formatted(point, worldName));
            }
        }

        this.fusion.log("warn", "Banner Layer Debug: Banner Name: %s, (%s), [%s,%s,%s]".formatted(name, point, type, key, path));
    }

    public void removeBanner(@NotNull final Position position, @NotNull final String bannerName, @NotNull final String worldName, @NotNull final String bannerType) {
        final int x = position.x();
        final int y = position.y();
        final int z = position.z();

        final String point = "%s,%s,%s,%s".formatted(x, y, z, bannerName);

        if (!this.markers.containsKey(position)) {
            this.fusion.log("warn", "The cache does not contain (%s)".formatted(point));

            return;
        }

        this.markers.remove(position);

        try {
            final BasicConfigurationNode root = node(worldName, bannerType);

            final List<String> locations = ConfigUtils.getStringList(root);

            if (locations.remove(point)) {
                this.fusion.log("warn", "Successfully removed %s from banners.json".formatted(point));

                root.setList(String.class, locations);

                Files.banners.save();
            }
        } catch (final Exception exception) {
            this.fusion.log("warn", "Failed to remove %s for %s".formatted(point, worldName), exception);
        }
    }

    public BasicConfigurationNode node(@NotNull final String worldName, @NotNull final String bannerType) {
        final BasicConfigurationNode root = Files.banners.getJsonConfiguration().node("banners", worldName);

        if (bannerType.isEmpty()) {
            return root;
        }

        return root.node(bannerType);
    }
}
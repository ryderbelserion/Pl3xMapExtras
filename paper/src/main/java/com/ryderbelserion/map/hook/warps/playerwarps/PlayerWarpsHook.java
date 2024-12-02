package com.ryderbelserion.map.hook.warps.playerwarps;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import com.olziedev.playerwarps.api.PlayerWarpsAPI;
import com.olziedev.playerwarps.api.warp.WCategory;
import com.olziedev.playerwarps.api.warp.WLocation;
import com.olziedev.playerwarps.api.warp.Warp;
import com.ryderbelserion.map.util.ConfigUtil;
import net.md_5.bungee.api.ChatColor;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import com.ryderbelserion.map.hook.Hook;
import org.jetbrains.annotations.NotNull;

public class PlayerWarpsHook implements Hook {

    private final String imageKey;
    private final String shadowKey;
    private final Options options;

    public PlayerWarpsHook() {
        PlayerWarpsConfig.reload();
        this.imageKey = String.format("pl3xmap_warps_%s", PlayerWarpsConfig.ICON_IMAGE);
        this.shadowKey = String.format("pl3xmap_warps_%s", PlayerWarpsConfig.ICON_SHADOW_IMAGE);
        this.options = new Options.Builder()
                .tooltipPane(PlayerWarpsConfig.ICON_TOOLTIP_PANE)
                .tooltipOffset(PlayerWarpsConfig.ICON_TOOLTIP_OFFSET)
                .tooltipDirection(PlayerWarpsConfig.ICON_TOOLTIP_DIRECTION)
                .tooltipPermanent(PlayerWarpsConfig.ICON_TOOLTIP_PERMANENT)
                .tooltipSticky(PlayerWarpsConfig.ICON_TOOLTIP_STICKY)
                .tooltipOpacity(PlayerWarpsConfig.ICON_TOOLTIP_OPACITY)
                .popupPane(PlayerWarpsConfig.ICON_POPUP_PANE)
                .popupOffset(PlayerWarpsConfig.ICON_POPUP_OFFSET)
                .popupMaxWidth(PlayerWarpsConfig.ICON_POPUP_MAX_WIDTH)
                .popupMinWidth(PlayerWarpsConfig.ICON_POPUP_MIN_WIDTH)
                .popupMaxHeight(PlayerWarpsConfig.ICON_POPUP_MAX_HEIGHT)
                .popupShouldAutoPan(PlayerWarpsConfig.ICON_POPUP_SHOULD_AUTO_PAN)
                .popupAutoPanPaddingTopLeft(PlayerWarpsConfig.ICON_POPUP_AUTO_PAN_PADDING_TOP_LEFT)
                .popupAutoPanPaddingBottomRight(PlayerWarpsConfig.ICON_POPUP_AUTO_PAN_PADDING_BOTTOM_RIGHT)
                .popupAutoPanPadding(PlayerWarpsConfig.ICON_POPUP_AUTO_PAN_PADDING)
                .popupShouldKeepInView(PlayerWarpsConfig.ICON_POPUP_SHOULD_KEEP_IN_VIEW)
                .popupCloseButton(PlayerWarpsConfig.ICON_POPUP_CLOSE_BUTTON)
                .popupShouldAutoClose(PlayerWarpsConfig.ICON_POPUP_SHOULD_AUTO_CLOSE)
                .popupShouldCloseOnEscapeKey(PlayerWarpsConfig.ICON_POPUP_SHOULD_CLOSE_ON_ESCAPE_KEY)
                .popupShouldCloseOnClick(PlayerWarpsConfig.ICON_POPUP_SHOULD_CLOSE_ON_CLICK)
                .build();
    }

    @Override
    public void registerWorld(@NotNull World world) {
        world.getLayerRegistry().register(new PlayerWarpsLayer(this, world));
    }

    @Override
    public void unloadWorld(@NotNull World world) {
        world.getLayerRegistry().unregister(PlayerWarpsLayer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull World world) {
        if (!ConfigUtil.isWarpsEnabled()) return EMPTY_LIST;

        return PlayerWarpsAPI.getInstance().getPlayerWarps(PlayerWarpsConfig.SHOW_LOCKED).stream()
                .filter(warp -> warp.getWarpLocation().getWorld().equals(world.getName()) || warp.isWarpLocked())
                .map(this::createIcon).collect(Collectors.toList());
    }

    private Icon createIcon(Warp warp) {
        WLocation loc = warp.getWarpLocation();
        Point point = Point.of(loc.getX(), loc.getZ());
        String key = String.format("playerwarps_%s_%s_%s", loc.getWorld(), warp.getWarpPlayer().getName(), warp.getWarpName());
        Icon icon = Marker.icon(key, point, this.imageKey, PlayerWarpsConfig.ICON_SIZE)
                .setAnchor(PlayerWarpsConfig.ICON_ANCHOR)
                .setRotationAngle(PlayerWarpsConfig.ICON_ROTATION_ANGLE)
                .setRotationOrigin(PlayerWarpsConfig.ICON_ROTATION_ORIGIN)
                .setShadow(this.shadowKey)
                .setShadowSize(PlayerWarpsConfig.ICON_SHADOW_SIZE)
                .setShadowAnchor(PlayerWarpsConfig.ICON_SHADOW_ANCHOR);
        Options.Builder builder = this.options.asBuilder();
        if (PlayerWarpsConfig.ICON_POPUP_CONTENT != null) {
            builder.popupContent(populateTooltip(PlayerWarpsConfig.ICON_POPUP_CONTENT, warp));
        }
        if (PlayerWarpsConfig.ICON_TOOLTIP_CONTENT != null) {
            builder.tooltipContent(populateTooltip(PlayerWarpsConfig.ICON_TOOLTIP_CONTENT, warp));
        }
        return icon.setOptions(builder.build());
    }

    private String populateTooltip(String value, Warp warp) {
        return value
                .replace("<warp>", warp.getWarpName())
                .replace("<owner>", warp.getWarpPlayer().getName())
                .replace("<visits>", Integer.toString(warp.getWarpVisit().getWarpVisits()))
                .replace("<desc>", strip(warp.getWarpDescription(true)))
                .replace("<date>", formatDate(warp.getWarpDate()))
                .replace("<category>", warp.getWarpCategory().stream().map(WCategory::getName).collect(Collectors.joining()))
                .replace("<rates>", strip(warp.getWarpRate().getRates().entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue().getRate()).collect(Collectors.joining())))
                .replace("<rates-avg>", strip(Double.toString(warp.getWarpRate().getRateAverage())))
                .replace("<rates-stars>", strip(warp.getWarpRate().getRateStars()));
    }

    private String strip(String str) {
        return ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', str));
    }

    private String formatDate(long timestamp) {
        return timestamp + "";
    }
}
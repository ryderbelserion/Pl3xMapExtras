package com.ryderbelserion.map.hook.warps.essentials;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Warps;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import com.ryderbelserion.map.hook.Hook;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class EssentialsHook implements Listener, Hook {

    private final String imageKey;
    private final String shadowKey;
    private final Options options;

    public EssentialsHook() {
        EssentialsConfig.reload();
        this.imageKey = String.format("pl3xmap_warps_%s", EssentialsConfig.ICON_IMAGE);
        this.shadowKey = String.format("pl3xmap_warps_%s", EssentialsConfig.ICON_SHADOW_IMAGE);
        this.options = new Options.Builder()
                .tooltipPane(EssentialsConfig.ICON_TOOLTIP_PANE)
                .tooltipOffset(EssentialsConfig.ICON_TOOLTIP_OFFSET)
                .tooltipDirection(EssentialsConfig.ICON_TOOLTIP_DIRECTION)
                .tooltipPermanent(EssentialsConfig.ICON_TOOLTIP_PERMANENT)
                .tooltipSticky(EssentialsConfig.ICON_TOOLTIP_STICKY)
                .tooltipOpacity(EssentialsConfig.ICON_TOOLTIP_OPACITY)
                .popupPane(EssentialsConfig.ICON_POPUP_PANE)
                .popupOffset(EssentialsConfig.ICON_POPUP_OFFSET)
                .popupMaxWidth(EssentialsConfig.ICON_POPUP_MAX_WIDTH)
                .popupMinWidth(EssentialsConfig.ICON_POPUP_MIN_WIDTH)
                .popupMaxHeight(EssentialsConfig.ICON_POPUP_MAX_HEIGHT)
                .popupShouldAutoPan(EssentialsConfig.ICON_POPUP_SHOULD_AUTO_PAN)
                .popupAutoPanPaddingTopLeft(EssentialsConfig.ICON_POPUP_AUTO_PAN_PADDING_TOP_LEFT)
                .popupAutoPanPaddingBottomRight(EssentialsConfig.ICON_POPUP_AUTO_PAN_PADDING_BOTTOM_RIGHT)
                .popupAutoPanPadding(EssentialsConfig.ICON_POPUP_AUTO_PAN_PADDING)
                .popupShouldKeepInView(EssentialsConfig.ICON_POPUP_SHOULD_KEEP_IN_VIEW)
                .popupCloseButton(EssentialsConfig.ICON_POPUP_CLOSE_BUTTON)
                .popupShouldAutoClose(EssentialsConfig.ICON_POPUP_SHOULD_AUTO_CLOSE)
                .popupShouldCloseOnEscapeKey(EssentialsConfig.ICON_POPUP_SHOULD_CLOSE_ON_ESCAPE_KEY)
                .popupShouldCloseOnClick(EssentialsConfig.ICON_POPUP_SHOULD_CLOSE_ON_CLICK)
                .build();
    }

    @Override
    public void registerWorld(@NotNull World world) {
        world.getLayerRegistry().register(new EssentialsLayer(this, world));
    }

    @Override
    public void unloadWorld(@NotNull World world) {
        world.getLayerRegistry().unregister(EssentialsLayer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull World world) {
        Map<String, Location> map = new HashMap<>();
        Warps warps = Essentials.getPlugin(Essentials.class).getWarps();

        for (String warp : warps.getList()) {
            try {
                map.put(warp, warps.getWarp(warp));
            } catch (Exception ignore) {}
        }

        return map.entrySet().stream()
                .filter(warp -> warp.getValue().getWorld().getName().equals(world.getName()))
                .map(this::createIcon).collect(Collectors.toList());
    }

    private Icon createIcon(Map.Entry<String, Location> warp) {
        String name = warp.getKey();
        Location loc = warp.getValue();
        Point point = Point.of(loc.getX(), loc.getZ());
        String key = String.format("essentialswarps_%s_%s", loc.getWorld(), name);

        Icon icon = Marker.icon(key, point, this.imageKey, EssentialsConfig.ICON_SIZE)
                .setAnchor(EssentialsConfig.ICON_ANCHOR)
                .setRotationAngle(EssentialsConfig.ICON_ROTATION_ANGLE)
                .setRotationOrigin(EssentialsConfig.ICON_ROTATION_ORIGIN)
                .setShadow(this.shadowKey)
                .setShadowSize(EssentialsConfig.ICON_SHADOW_SIZE)
                .setShadowAnchor(EssentialsConfig.ICON_SHADOW_ANCHOR);
        Options.Builder builder = this.options.asBuilder();

        if (EssentialsConfig.ICON_POPUP_CONTENT != null) {
            builder.popupContent(EssentialsConfig.ICON_POPUP_CONTENT.replace("<warp>", name));
        }

        if (EssentialsConfig.ICON_TOOLTIP_CONTENT != null) {
            builder.tooltipContent(EssentialsConfig.ICON_TOOLTIP_CONTENT.replace("<warp>", name));
        }

        return icon.setOptions(builder.build());
    }
}
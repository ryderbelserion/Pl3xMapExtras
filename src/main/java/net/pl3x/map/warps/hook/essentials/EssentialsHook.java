/*
 * MIT License
 *
 * Copyright (c) 2020-2023 William Blake Galbreath
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.pl3x.map.warps.hook.essentials;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.Warps;
import com.earth2me.essentials.commands.WarpNotFoundException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import net.ess3.api.InvalidWorldException;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.world.World;
import net.pl3x.map.warps.hook.Hook;
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
    public @NotNull Collection<Marker<?>> getWarps(@NotNull World world) {
        Map<String, Location> map = new HashMap<>();
        Warps warps = Essentials.getPlugin(Essentials.class).getWarps();
        for (String warp : warps.getList()) {
            try {
                map.put(warp, warps.getWarp(warp));
            } catch (WarpNotFoundException | InvalidWorldException ignore) {
            }
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

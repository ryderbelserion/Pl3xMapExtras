package com.ryderbelserion.map.listener.banners;

import com.ryderbelserion.map.util.ItemUtil;
import java.util.concurrent.ThreadLocalRandom;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.config.BannerConfig;
import com.ryderbelserion.map.marker.banners.Banner;
import com.ryderbelserion.map.marker.banners.BannersLayer;
import com.ryderbelserion.map.marker.banners.Icon;
import com.ryderbelserion.map.marker.banners.Position;
import com.ryderbelserion.map.util.ConfigUtil;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BannerListener implements Listener {

    @EventHandler
    public void onClickBanner(@NotNull PlayerInteractEvent event) {
        if (!ConfigUtil.isBannersEnabled()) return;

        final Block block = event.getClickedBlock();

        if (block == null) {
            // no block was clicked; ignore
            return;
        }

        final BlockState state = block.getState();

        if (!(state instanceof org.bukkit.block.Banner banner)) {
            // clicked block is not a banner; ignore
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.FILLED_MAP) {
            // player was not holding a filled map; ignore
            return;
        }

        if (!Permissions.banners_admin.hasPermission(event.getPlayer())) {
            // player does not have permission; ignore
            return;
        }

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> {
                // cancel event to stop banner from breaking
                event.setCancelled(true);

                tryRemoveBanner(banner);
            }

            case RIGHT_CLICK_BLOCK -> tryAddBanner(banner);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBannerPlace(@NotNull BlockPlaceEvent event) {
        if (!ConfigUtil.isBannersEnabled() || !BannerConfig.banners_block_place) return;

        tryAddBanner(event.getBlock().getState(false));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBannerBreak(@NotNull BlockBreakEvent event) {
        if (!ConfigUtil.isBannersEnabled()) return;

        tryRemoveBanner(event.getBlock().getState(false));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBannerBreak(@NotNull BlockDestroyEvent event) {
        if (!ConfigUtil.isBannersEnabled()) return;

        tryRemoveBanner(event.getBlock().getState(false));
    }

    protected void tryAddBanner(@NotNull final BlockState state) {
        if (state instanceof org.bukkit.block.Banner banner) {

            final Location loc = banner.getLocation();
            final Position pos = new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

            tryAddBanner(banner, pos);
        }
    }

    protected void tryAddBanner(@NotNull final org.bukkit.block.Banner banner, @NotNull final Position pos) {
        final BannersLayer layer = getLayer(banner);

        if (layer == null) {
            // world has no banners layer; ignore
            return;
        }

        final Icon icon = Icon.get(banner.getType());

        layer.putBanner(new Banner(pos, icon, banner.getCustomName()));

        // play fancy particles as visualizer
        particles(banner.getLocation(), ItemUtil.getParticleType(layer.getConfig().BANNER_ADD_PARTICLES), ItemUtil.getSound(layer.getConfig().BANNER_ADD_SOUND));
    }

    protected void tryRemoveBanner(@NotNull final BlockState state) {
        if (state instanceof org.bukkit.block.Banner banner) {
            tryRemoveBanner(banner);
        }
    }

    protected void tryRemoveBanner(@NotNull final org.bukkit.block.Banner banner) {
        final BannersLayer layer = getLayer(banner);

        if (layer == null) {
            // world has no banners layer; ignore
            return;
        }

        final Location loc = banner.getLocation();

        final Position pos = new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        // if it does not contain position, return.
        if (!layer.containsBanner(pos)) {
            return;
        }

        layer.removeBanner(pos);

        // play fancy particles as visualizer
        particles(banner.getLocation(), ItemUtil.getParticleType(layer.getConfig().BANNER_REMOVE_PARTICLES), ItemUtil.getSound(layer.getConfig().BANNER_REMOVE_SOUND));
    }

    protected @Nullable BannersLayer getLayer(@NotNull final BlockState state) {
        final World world = Pl3xMap.api().getWorldRegistry().get(state.getWorld().getName());

        if (world == null || !world.isEnabled()) {
            // world is missing or not enabled; ignore
            return null;
        }

        return (BannersLayer) world.getLayerRegistry().get(BannersLayer.KEY);
    }

    protected void particles(@NotNull final Location loc, @Nullable final Particle particle, @Nullable final Sound sound) {
        final org.bukkit.World world = loc.getWorld();

        if (sound != null) {
            world.playSound(loc, sound, 1.0F, 1.0F);
        }

        if (particle != null) {
            final ThreadLocalRandom rand = ThreadLocalRandom.current();

            for (int i = 0; i < 20; ++i) {
                double x = loc.getX() + rand.nextGaussian();
                double y = loc.getY() + rand.nextGaussian();
                double z = loc.getZ() + rand.nextGaussian();

                world.spawnParticle(particle, x, y, z, 1, 0, 0, 0, 0, null, true);
            }
        }
    }
}

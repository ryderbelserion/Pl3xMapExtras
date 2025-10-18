package com.ryderbelserion.map.banners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.objects.Position;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;

public class PaperBannerListener implements Listener {

    private final BannerRegistry registry;

    public PaperBannerListener(@NotNull final Pl3xMapCommon plugin) {
        this.registry = plugin.getBannerRegistry();
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        final Block block = event.getClickedBlock();

        if (block == null) return;

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        final Player player = event.getPlayer();

        final PlayerInventory inventory = player.getInventory();

        if (inventory.getItemInMainHand().getType() != Material.FILLED_MAP) return;

        if (!player.hasPermission("pl3xmapextras.banners.admin")) return;

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> {
                event.setCancelled(true);

                removeBanner(banner);
            }

            case RIGHT_CLICK_BLOCK -> addBanner(banner);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        addBanner(banner);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        removeBanner(banner);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDestroy(BlockDestroyEvent event) {
        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        removeBanner(banner);
    }

    public void addBanner(@NotNull final Banner banner) {
        final World world = banner.getWorld();

        final int x = banner.getX();
        final int y = banner.getY();
        final int z = banner.getZ();

        final Component component = banner.customName();

        final Material material = banner.getType();
        final Key key = material.key();

        this.registry.addBanner(new Position(x, y, z), world.getName(), key, component == null ? Component.translatable(material.translationKey()) : component);
    }

    public void removeBanner(@NotNull final Banner banner) {
        final World world = banner.getWorld();

        final int x = banner.getX();
        final int y = banner.getY();
        final int z = banner.getZ();

        final String minimal = banner.getType().key().asMinimalString();
        final String value = minimal.endsWith("wall_banner") ? minimal.replace("_wall_banner", "") : minimal.replace("_banner", "");

        this.registry.removeBanner(new Position(x, y, z), world.getName(), value);
    }
}
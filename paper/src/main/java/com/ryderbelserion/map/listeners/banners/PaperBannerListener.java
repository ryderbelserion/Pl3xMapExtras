package com.ryderbelserion.map.listeners.banners;

import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.banners.BannerLayer;
import com.ryderbelserion.map.banners.BannerRegistry;
import com.ryderbelserion.map.constants.Namespaces;
import com.ryderbelserion.map.objects.MapPosition;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.pl3x.map.core.Pl3xMap;
import org.bukkit.Chunk;
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
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaperBannerListener implements Listener {

    private final BannerRegistry registry;
    private final Pl3xMapCommon plugin;

    public PaperBannerListener(@NotNull final Pl3xMapCommon plugin) {
        this.registry = plugin.getBannerRegistry();
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (!this.plugin.getBannerConfig().isBlockInteract()) return;

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

                removeBanner(player, banner);
            }

            case RIGHT_CLICK_BLOCK -> addBanner(player, banner);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (!this.plugin.getBannerConfig().isBlockPlace()) return;

        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        addBanner(event.getPlayer(), banner);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event) {
        if (!this.plugin.getBannerConfig().isBlockPlace()) return;

        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        removeBanner(event.getPlayer(), banner);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockDestroy(BlockDestroyEvent event) {
        if (!this.plugin.getBannerConfig().isBlockPlace()) return;

        final Block block = event.getBlock();

        final BlockState state = block.getState(false);

        if (!(state instanceof Banner banner)) return;

        removeBanner(Audience.empty(), banner);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkUnload(ChunkUnloadEvent event) {
        if (!this.plugin.getBannerConfig().isBlockPlace()) return;

        final Chunk chunk = event.getChunk();

        inspectChunk(chunk);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(ChunkLoadEvent event) {
        if (!this.plugin.getBannerConfig().isBlockPlace()) return;

        if (event.isNewChunk()) {
            return;
        }

        final Chunk chunk = event.getChunk();

        inspectChunk(chunk);
    }

    public void addBanner(@NotNull final Audience audience, @NotNull final Banner banner) {
        final World world = banner.getWorld();

        final int x = banner.getX();
        final int y = banner.getY();
        final int z = banner.getZ();

        final Material material = banner.getType();
        final Key key = material.key();

        this.registry.addBanner(audience, new MapPosition(x, y, z), asComponent(material, banner.customName()), world.getName(), key);
    }

    public void removeBanner(@NotNull final Audience audience, @NotNull final Banner banner) {
        final World world = banner.getWorld();

        final int x = banner.getX();
        final int y = banner.getY();
        final int z = banner.getZ();

        final Component component = banner.customName();
        final Material material = banner.getType();
        final Key key = material.key();

        final String minimal = key.asMinimalString();
        final String displayItem = minimal.endsWith("wall_banner") ? minimal.replace("_wall_banner", "") : minimal.replace("_banner", "");

        this.registry.removeBanner(audience, new MapPosition(x, y, z), asComponent(material, component), displayItem, world.getName());
    }

    public void inspectChunk(@NotNull final Chunk chunk) {
        final World bukkitWorld = chunk.getWorld();

        final net.pl3x.map.core.world.World world = Pl3xMap.api().getWorldRegistry().get(bukkitWorld.getName());

        if (world == null) {
            return;
        }

        final BannerLayer layer = (BannerLayer) world.getLayerRegistry().get(Namespaces.banner_key);

        if (layer == null) {
            return;
        }

        int minX = chunk.getX();
        int minZ = chunk.getZ();
        int maxX = minX + 16;
        int maxZ = minZ + 16;

        layer.getBanners().stream()
                // filter banners only inside chunk
                .filter(banner -> banner.position().x() >= minX)
                .filter(banner -> banner.position().z() >= minZ)
                .filter(banner -> banner.position().x() <= maxX)
                .filter(banner -> banner.position().z() <= maxZ)
                .filter(banner -> {
                    final MapPosition position = banner.position();

                    final int x = position.x();
                    final int y = position.y();
                    final int z = position.z();

                    return !(bukkitWorld.getBlockAt(x, y, z).getState(false) instanceof Banner);
                })
                .forEach(banner -> layer.removeBanner(banner, true));
    }

    private @NotNull String asComponent(@NotNull final Material material, @Nullable final Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component == null ? Component.translatable(material.translationKey()) : component);
    }
}
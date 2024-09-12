package com.ryderbelserion.map.listener.banners;

import com.ryderbelserion.map.config.BannerConfig;
import com.ryderbelserion.map.marker.banners.BannersLayer;
import com.ryderbelserion.map.marker.banners.Icon;
import com.ryderbelserion.map.util.ModuleUtil;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.world.World;
import org.bukkit.Chunk;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.jetbrains.annotations.NotNull;

public class BannerWorldListener implements EventListener, Listener {

    public BannerWorldListener() {
        if (!ModuleUtil.isBannersEnabled()) return;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(@NotNull ChunkLoadEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        if (event.isNewChunk()) {
            // chunk is new; ignore
            return;
        }

        checkChunk(event.getChunk());
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        checkChunk(event.getChunk());
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        Icon.register();
        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!ModuleUtil.isBannersEnabled()) return;

        try {
            event.getWorld().getLayerRegistry().unregister(BannersLayer.KEY);
        } catch (Throwable ignore) {
        }
    }

    private void registerWorld(@NotNull World world) {
        if (!ModuleUtil.isBannersEnabled()) return;

        world.getLayerRegistry().register(new BannersLayer(new BannerConfig(world)));
    }

    private void checkChunk(@NotNull Chunk chunk) {
        if (!ModuleUtil.isBannersEnabled()) return;

        org.bukkit.World bukkitWorld = chunk.getWorld();

        World world = Pl3xMap.api().getWorldRegistry().get(bukkitWorld.getName());
        if (world == null) {
            // world is missing or not enabled; ignore
            return;
        }

        BannersLayer layer = (BannersLayer) world.getLayerRegistry().get(BannersLayer.KEY);
        if (layer == null) {
            // world has no banners layer; ignore
            return;
        }


        int minX = chunk.getX();
        int minZ = chunk.getZ();
        int maxX = minX + 16;
        int maxZ = minZ + 16;

        layer.getBanners().stream()
                // filter banners only inside chunk
                .filter(banner -> banner.pos().x() >= minX)
                .filter(banner -> banner.pos().z() >= minZ)
                .filter(banner -> banner.pos().x() <= maxX)
                .filter(banner -> banner.pos().z() <= maxZ)
                // filter banners that are no longer there
                .filter(banner -> !banner.isBanner(bukkitWorld))
                // remove all matching banners
                .forEach(banner -> layer.removeBanner(banner.pos()));
    }
}
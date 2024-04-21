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
package com.ryderbelserion.map.listener.banners;

import com.ryderbelserion.map.config.BannerConfig;
import com.ryderbelserion.map.markers.banners.BannersLayer;
import com.ryderbelserion.map.markers.banners.Icon;
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
        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(@NotNull ChunkLoadEvent event) {
        if (event.isNewChunk()) {
            // chunk is new; ignore
            return;
        }
        checkChunk(event.getChunk());
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
        checkChunk(event.getChunk());
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        Icon.register();
        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        try {
            event.getWorld().getLayerRegistry().unregister(BannersLayer.KEY);
        } catch (Throwable ignore) {
        }
    }

    private void registerWorld(@NotNull World world) {
        world.getLayerRegistry().register(new BannersLayer(new BannerConfig(world)));
    }

    private void checkChunk(@NotNull Chunk chunk) {
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
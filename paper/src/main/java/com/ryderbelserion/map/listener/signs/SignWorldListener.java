package com.ryderbelserion.map.listener.signs;

import com.ryderbelserion.map.config.v1.SignsConfig;
import com.ryderbelserion.map.util.ConfigUtil;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.event.EventHandler;
import net.pl3x.map.core.event.EventListener;
import net.pl3x.map.core.event.server.Pl3xMapEnabledEvent;
import net.pl3x.map.core.event.server.ServerLoadedEvent;
import net.pl3x.map.core.event.world.WorldLoadedEvent;
import net.pl3x.map.core.event.world.WorldUnloadedEvent;
import net.pl3x.map.core.world.World;
import com.ryderbelserion.map.marker.signs.Icon;
import com.ryderbelserion.map.marker.signs.SignsLayer;
import org.bukkit.Chunk;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.jetbrains.annotations.NotNull;

public class SignWorldListener implements EventListener, Listener {

    public SignWorldListener() {
        if (!ConfigUtil.isSignsEnabled()) return;

        Pl3xMap.api().getEventRegistry().register(this);
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkLoad(@NotNull ChunkLoadEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        if (event.isNewChunk()) {
            // chunk is new; ignore
            return;
        }

        checkChunk(event.getChunk());
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    public void onChunkUnload(@NotNull ChunkUnloadEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        checkChunk(event.getChunk());
    }

    @EventHandler
    public void onPl3xMapEnabled(@NotNull Pl3xMapEnabledEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        Icon.register();
    }

    @EventHandler
    public void onServerLoaded(@NotNull ServerLoadedEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        Icon.register();
        Pl3xMap.api().getWorldRegistry().forEach(this::registerWorld);
    }

    @EventHandler
    public void onWorldLoaded(@NotNull WorldLoadedEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        registerWorld(event.getWorld());
    }

    @EventHandler
    public void onWorldUnloaded(@NotNull WorldUnloadedEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        try {
            event.getWorld().getLayerRegistry().unregister(SignsLayer.KEY);
        } catch (Throwable ignore) {}
    }

    private void registerWorld(@NotNull final World world) {
        world.getLayerRegistry().register(new SignsLayer(new SignsConfig(world)));
    }

    private void checkChunk(@NotNull final Chunk chunk) {
        final org.bukkit.World bukkitWorld = chunk.getWorld();

        final World world = Pl3xMap.api().getWorldRegistry().get(bukkitWorld.getName());

        if (world == null) {
            // world is missing or not enabled; ignore
            return;
        }

        final SignsLayer layer = (SignsLayer) world.getLayerRegistry().get(SignsLayer.KEY);

        if (layer == null) {
            // world has no signs layer; ignore
            return;
        }


        int minX = chunk.getX();
        int minZ = chunk.getZ();
        int maxX = minX + 16;
        int maxZ = minZ + 16;

        layer.getSigns().stream()
                // filter signs only inside chunk
                .filter(sign -> sign.pos().x() >= minX)
                .filter(sign -> sign.pos().z() >= minZ)
                .filter(sign -> sign.pos().x() <= maxX)
                .filter(sign -> sign.pos().z() <= maxZ)
                // filter signs that are no longer there
                .filter(sign -> !sign.isSign(bukkitWorld))
                // remove all matching signs
                .forEach(sign -> layer.removeSign(sign.pos()));
    }
}
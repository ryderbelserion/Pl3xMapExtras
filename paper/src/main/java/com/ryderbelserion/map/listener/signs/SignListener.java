package com.ryderbelserion.map.listener.signs;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import com.destroystokyo.paper.event.block.BlockDestroyEvent;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.util.ConfigUtil;
import com.ryderbelserion.map.util.ItemUtil;
import com.ryderbelserion.map.util.ModuleUtil;
import net.pl3x.map.core.Pl3xMap;
import net.pl3x.map.core.world.World;
import com.ryderbelserion.map.marker.signs.Icon;
import com.ryderbelserion.map.marker.signs.Position;
import com.ryderbelserion.map.marker.signs.Sign;
import com.ryderbelserion.map.marker.signs.SignsLayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Directional;
import org.bukkit.block.sign.Side;
import org.bukkit.block.sign.SignSide;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SignListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignEdit(@NotNull SignChangeEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        if (!Permissions.signs_admin.hasPermission(event.getPlayer())) {
            // player doesn't have permission to track signs; ignore
            return;
        }

        final BlockState state = event.getBlock().getState(false);

        final SignsLayer layer = getLayer(state);

        if (layer == null) {
            // world doesn't have a signs layer; ignore
            return;
        }

        final Location loc = state.getLocation();
        final Position pos = new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        if (!layer.hasSign(pos)) {
            // not tracking any signs here; ignore
            return;
        }

        tryAddSign(state, pos, event.getSide());
    }


    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockDestroyEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        tryRemoveSign(event.getBlock().getState());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onClickSign(@NotNull PlayerInteractEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        final Block block = event.getClickedBlock();

        if (block == null) {
            // no block was clicked; ignore
            return;
        }

        final BlockState state = block.getState();

        if (!(state instanceof org.bukkit.block.Sign sign)) {
            // clicked block is not a sign; ignore
            return;
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getType() != Material.FILLED_MAP) {
            // player was not holding a filled map; ignore
            return;
        }

        if (!Permissions.signs_admin.hasPermission(event.getPlayer())) {
            // player doesn't have permission to track signs; ignore
            return;
        }

        switch (event.getAction()) {
            case LEFT_CLICK_BLOCK -> {
                // cancel event to stop sign from breaking
                event.setCancelled(true);

                tryRemoveSign(sign);
            }

            case RIGHT_CLICK_BLOCK -> {
                // cancel event to stop sign editor from opening
                event.setCancelled(true);

                BlockFace facing = event.getBlockFace();

                if (state.getBlockData() instanceof Directional directional) {
                    facing = directional.getFacing();
                }

                tryAddSign(sign, sign.getSide(event.getBlockFace() == facing ? Side.FRONT : Side.BACK));
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockDropItemEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        tryRemoveSign(event.getBlockState());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockBurnEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        tryRemoveSign(event.getBlock().getState());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockExplodeEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        event.blockList().forEach(block -> tryRemoveSign(block.getState()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull EntityExplodeEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        event.blockList().forEach(block -> tryRemoveSign(block.getState()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockPistonExtendEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        event.getBlocks().forEach(block -> tryRemoveSign(block.getState()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockPistonRetractEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        event.getBlocks().forEach(block -> tryRemoveSign(block.getState()));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onSignBreak(@NotNull BlockFromToEvent event) {
        if (!ConfigUtil.isSignsEnabled()) return;

        tryRemoveSign(event.getToBlock().getState());
    }

    private void tryAddSign(@NotNull final BlockState state, @NotNull final Position pos, @NotNull final Side side) {
        if (state instanceof org.bukkit.block.Sign sign) {
            tryAddSign(sign, pos, sign.getSide(side));
        }
    }

    private void tryAddSign(@NotNull final BlockState state, @NotNull final SignSide side) {
        if (state instanceof org.bukkit.block.Sign sign) {
            Location loc = sign.getLocation();
            Position pos = new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            tryAddSign(sign, pos, side);
        }
    }

    private void tryAddSign(@NotNull final org.bukkit.block.Sign sign, @NotNull final Position pos, @NotNull final SignSide side) {
        final SignsLayer layer = getLayer(sign);

        if (layer == null) {
            // world has no signs layer; ignore
            return;
        }

        final Icon icon = Icon.get(sign);

        if (icon == null) {
            // material is not a registered sign; ignore
            return;
        }

        // add sign to layer
        layer.putSign(new Sign(pos, icon, getLines(side)));

        // play fancy particles as visualizer
        particles(sign.getLocation(), ItemUtil.getParticleType(layer.getConfig().SIGN_ADD_PARTICLES), ItemUtil.getSound(layer.getConfig().SIGN_ADD_SOUND));
    }

    protected void tryRemoveSign(@NotNull final BlockState state) {
        if (state instanceof org.bukkit.block.Sign sign) {
            tryRemoveSign(sign);
        }
    }

    private void tryRemoveSign(@NotNull final org.bukkit.block.Sign sign) {
        final SignsLayer layer = getLayer(sign);

        if (layer == null) {
            // world has no signs layer; ignore
            return;
        }

        final Location loc = sign.getLocation();
        final Position pos = new Position(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());

        layer.removeSign(pos);

        // play fancy particles as visualizer
        particles(sign.getLocation(), ItemUtil.getParticleType(layer.getConfig().SIGN_REMOVE_PARTICLES), ItemUtil.getSound(layer.getConfig().SIGN_REMOVE_SOUND));
    }

    protected List<String> getLines(@NotNull final SignSide side) {
        return List.of(side.getLines()); //todo() deprecated
    }

    protected @Nullable SignsLayer getLayer(@NotNull final BlockState state) {
        final World world = Pl3xMap.api().getWorldRegistry().get(state.getWorld().getName());

        if (world == null || !world.isEnabled()) {
            // world is missing or not enabled; ignore
            return null;
        }

        return (SignsLayer) world.getLayerRegistry().get(SignsLayer.KEY);
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
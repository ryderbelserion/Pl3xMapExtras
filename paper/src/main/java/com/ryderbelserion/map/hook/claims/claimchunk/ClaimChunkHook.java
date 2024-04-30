package com.ryderbelserion.map.hook.claims.claimchunk;

import com.cjburkey.claimchunk.ClaimChunk;
import com.cjburkey.claimchunk.chunk.DataChunk;
import com.cjburkey.claimchunk.data.newdata.IClaimChunkDataHandler;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.util.ChunkMergeUtil;
import net.pl3x.map.core.markers.Point;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.marker.Rectangle;
import net.pl3x.map.core.markers.option.Fill;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Colors;
import net.pl3x.map.core.world.World;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class ClaimChunkHook implements Listener, Hook {

    public ClaimChunkHook() {
        ClaimChunkConfig.reload();
    }

    @Override
    public void registerWorld(@NotNull World world) {
        world.getLayerRegistry().register(new ClaimChunkLayer(this, world));
    }

    @Override
    public void unloadWorld(@NotNull World world) {
        world.getLayerRegistry().unregister(ClaimChunkLayer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull World world) {
        @SuppressWarnings("deprecation")
        ClaimChunk cc = ClaimChunk.getInstance();

        IClaimChunkDataHandler dataHandler;

        try {
            Field field = ClaimChunk.class.getDeclaredField("dataHandler");
            field.setAccessible(true);
            dataHandler = (IClaimChunkDataHandler) field.get(cc);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return EMPTY_LIST;
        }

        DataChunk[] chunkArr = dataHandler.getClaimedChunks();

        if (chunkArr == null) {
            return EMPTY_LIST;
        }

        List<DataChunk> chunks = Arrays.stream(chunkArr)
                .filter(claim -> claim.chunk.getWorld().equals(world.getName()))
                .toList();

        if (ClaimChunkConfig.SHOW_CHUNKS) {
            return chunks.stream().map(claim -> {
                int minX = claim.chunk.getX() << 4;
                int maxX = (claim.chunk.getX() + 1) << 4;
                int minZ = claim.chunk.getZ() << 4;
                int maxZ = (claim.chunk.getZ() + 1) << 4;
                String key = String.format("cc_%s_chunk_%d_%d", world.getName(), minX, minZ);
                Rectangle rect = Marker.rectangle(key, Point.of(minX, minZ), Point.of(maxX, maxZ));
                return rect.setOptions(options(world, claim.player));
            }).collect(Collectors.toList());
        }

        List<ClaimChunkClaim> claims = chunks.stream().map(chunk ->
                new ClaimChunkClaim(chunk.chunk.getX(), chunk.chunk.getZ(), chunk.player)
        ).collect(Collectors.toList());
        List<ClaimChunkGroup> groups = groupClaims(claims);
        Collection<Marker<?>> markers = new ArrayList<>();

        for (ClaimChunkGroup group : groups) {
            String key = String.format("cc_%s_chunk_%s", world.getName(), group.id());
            markers.add(ChunkMergeUtil.getPoly(key, group.claims())
                   .setOptions(options(world, group.owner())));
        }

        return markers;
    }

    private @NotNull Options.Builder options(@NotNull World world, @NotNull UUID owner) {
        OfflinePlayer player = Bukkit.getOfflinePlayer(owner);
        String ownerName = player.getName() == null ? "unknown" : player.getName();

        return Options.builder()
                .strokeColor(Colors.fromHex(ClaimChunkConfig.MARKER_STROKE_COLOR))
                .strokeWeight(ClaimChunkConfig.MARKER_STROKE_WEIGHT)
                .fillColor(Colors.fromHex(ClaimChunkConfig.MARKER_FILL_COLOR))
                .fillType(Fill.Type.NONZERO)
                .popupContent(ClaimChunkConfig.MARKER_POPUP
                        .replace("<world>", world.getName())
                        .replace("<owner>", ownerName)
                );
    }

    private @NotNull List<ClaimChunkGroup> groupClaims(@NotNull List<ClaimChunkClaim> claims) {
        // break groups down by owner
        Map<UUID, List<ClaimChunkClaim>> byOwner = new HashMap<>();
        for (ClaimChunkClaim claim : claims) {
            List<ClaimChunkClaim> list = byOwner.getOrDefault(claim.owner(), new ArrayList<>());
            list.add(claim);
            byOwner.put(claim.owner(), list);
        }

        // combine touching claims
        Map<UUID, List<ClaimChunkGroup>> groups = new HashMap<>();
        for (Map.Entry<UUID, List<ClaimChunkClaim>> entry : byOwner.entrySet()) {
            UUID owner = entry.getKey();
            List<ClaimChunkClaim> list = entry.getValue();
            next1:

            for (ClaimChunkClaim claim : list) {
                List<ClaimChunkGroup> groupList = groups.getOrDefault(owner, new ArrayList<>());
                for (ClaimChunkGroup group : groupList) {
                    if (group.isTouching(claim)) {
                        group.add(claim);
                        continue next1;
                    }
                }

                groupList.add(new ClaimChunkGroup(claim, owner));
                groups.put(owner, groupList);
            }
        }

        // combined touching groups
        List<ClaimChunkGroup> combined = new ArrayList<>();
        for (List<ClaimChunkGroup> list : groups.values()) {
            next:
            for (ClaimChunkGroup group : list) {
                for (ClaimChunkGroup toChk : combined) {
                    if (toChk.isTouching(group)) {
                        toChk.add(group);
                        continue next;
                    }
                }

                combined.add(group);
            }
        }

        return combined;
    }
}
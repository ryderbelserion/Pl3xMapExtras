package com.ryderbelserion.map.hook.claims.claimchunk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import com.cjburkey.claimchunk.ClaimChunk;
import com.cjburkey.claimchunk.chunk.DataChunk;
import com.cjburkey.claimchunk.data.newdata.IClaimChunkDataHandler;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.util.ChunkUtil;
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
    public void registerWorld(@NotNull final World world) {
        world.getLayerRegistry().register(new ClaimChunkLayer(this, world));
    }

    @Override
    public void unloadWorld(@NotNull final World world) {
        world.getLayerRegistry().unregister(ClaimChunkLayer.KEY);
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull final World world) {
        //if (!ConfigUtil.isClaimsEnabled()) return EMPTY_LIST;

        final ClaimChunk cc = ClaimChunk.getInstance();

        IClaimChunkDataHandler dataHandler;

        try {
            Field field = ClaimChunk.class.getDeclaredField("dataHandler"); //todo() nah wtf
            field.setAccessible(true);

            dataHandler = (IClaimChunkDataHandler) field.get(cc);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return EMPTY_LIST;
        }

        final DataChunk[] chunkArr = dataHandler.getClaimedChunks();

        if (chunkArr == null) {
            return EMPTY_LIST;
        }

        final List<DataChunk> chunks = Arrays.stream(chunkArr)
                .filter(claim -> claim.chunk.world().equals(world.getName()))
                .toList();

        if (ClaimChunkConfig.SHOW_CHUNKS) {
            return chunks.stream().map(claim -> {
                final int minX = claim.chunk.x() << 4;
                final int maxX = (claim.chunk.x() + 1) << 4;
                final int minZ = claim.chunk.z() << 4;
                final int maxZ = (claim.chunk.z() + 1) << 4;
                final String key = String.format("cc_%s_chunk_%d_%d", world.getName(), minX, minZ);
                final Rectangle rect = Marker.rectangle(key, Point.of(minX, minZ), Point.of(maxX, maxZ));

                return rect.setOptions(options(world, claim.player));
            }).collect(Collectors.toList());
        }

        final List<ClaimChunkClaim> claims = chunks.stream().map(chunk -> new ClaimChunkClaim(chunk.chunk.x(), chunk.chunk.x(), chunk.player)).collect(Collectors.toList());
        final List<ClaimChunkGroup> groups = groupClaims(claims);
        final Collection<Marker<?>> markers = new ArrayList<>();

        for (final ClaimChunkGroup group : groups) {
            final String key = String.format("cc_%s_chunk_%s", world.getName(), group.id());

            markers.add(ChunkUtil.getPoly(key, group.claims()).setOptions(options(world, group.owner())));
        }

        return markers;
    }

    private @NotNull Options.Builder options(@NotNull final World world, @NotNull final UUID owner) {
        final OfflinePlayer player = Bukkit.getOfflinePlayer(owner); //todo() wtf
        final String ownerName = player.getName() == null ? "unknown" : player.getName();

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

    private @NotNull List<ClaimChunkGroup> groupClaims(@NotNull final List<ClaimChunkClaim> claims) {
        // break groups down by owner
        final Map<UUID, List<ClaimChunkClaim>> byOwner = new HashMap<>();

        for (final ClaimChunkClaim claim : claims) {
            final List<ClaimChunkClaim> list = byOwner.getOrDefault(claim.owner(), new ArrayList<>());

            list.add(claim);

            byOwner.put(claim.owner(), list);
        }

        // combine touching claims
        Map<UUID, List<ClaimChunkGroup>> groups = new HashMap<>();

        for (final Map.Entry<UUID, List<ClaimChunkClaim>> entry : byOwner.entrySet()) {

            final UUID owner = entry.getKey();
            final List<ClaimChunkClaim> list = entry.getValue();

            next1:

            for (final ClaimChunkClaim claim : list) {
                final List<ClaimChunkGroup> groupList = groups.getOrDefault(owner, new ArrayList<>());

                for (final ClaimChunkGroup group : groupList) {
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
        final List<ClaimChunkGroup> combined = new ArrayList<>();

        for (final List<ClaimChunkGroup> list : groups.values()) {
            next:

            for (final ClaimChunkGroup group : list) {
                for (final ClaimChunkGroup toChk : combined) {

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
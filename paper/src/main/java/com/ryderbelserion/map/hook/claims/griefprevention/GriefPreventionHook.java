package com.ryderbelserion.map.hook.claims.griefprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.hook.Hook;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.util.Colors;
import net.pl3x.map.core.world.World;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class GriefPreventionHook implements Listener, Hook {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();

    private final Pl3xMapPaper platform = this.plugin.getPlatform();

    private final BasicConfig config = this.platform.getBasicConfig();

    private final Map<String, Map<String, Map<UUID, String>>> users = new HashMap<>();

    public GriefPreventionHook() {
        GriefPreventionConfig.reload();
    }

    private boolean isWorldEnabled(@NotNull final String name) {
        return GriefPrevention.instance.claimsEnabledForWorld(Bukkit.getWorld(name));
    }

    @Override
    public void registerWorld(@NotNull final World world) {
        final String name = world.getName();

        if (isWorldEnabled(name)) {
            this.users.put(name, new HashMap<>());

            world.getLayerRegistry().register(new GriefPreventionLayer(this, world));
        }
    }

    @Override
    public void unloadWorld(@NotNull final World world) {
        world.getLayerRegistry().unregister(GriefPreventionLayer.KEY);

        this.users.remove(world.getName());
    }

    @Override
    public @NotNull Collection<Marker<?>> getData(@NotNull final World world) {
        if (!this.config.isClaimsEnabled()) return EMPTY_LIST;

        if (!isWorldEnabled(world.getName())) {
            return EMPTY_LIST;
        }

        return GriefPrevention.instance.dataStore.getClaims().stream()
                .filter(claim -> claim.getLesserBoundaryCorner().getWorld().getName().equals(world.getName()))
                .map(claim -> new GriefPreventionClaim(world, claim))
                .map(claim -> {
                    String key = "gp-claim-" + claim.getID();
                    return Marker.rectangle(key, claim.getMin(), claim.getMax())
                            .setOptions(getOptions(claim));
                })
                .collect(Collectors.toSet());
    }

    private @NotNull Options getOptions(@NotNull final GriefPreventionClaim claim) {
        Options.Builder builder;

        if (claim.isAdminClaim()) {
            builder = Options.builder()
                    .strokeWeight(GriefPreventionConfig.MARKER_ADMIN_STROKE_WEIGHT)
                    .strokeColor(Colors.fromHex(GriefPreventionConfig.MARKER_ADMIN_STROKE_COLOR))
                    .fillColor(Colors.fromHex(GriefPreventionConfig.MARKER_ADMIN_FILL_COLOR))
                    .popupContent(processPopup(GriefPreventionConfig.MARKER_ADMIN_POPUP, claim));
        } else {
            builder = Options.builder()
                    .strokeWeight(GriefPreventionConfig.MARKER_BASIC_STROKE_WEIGHT)
                    .strokeColor(Colors.fromHex(GriefPreventionConfig.MARKER_BASIC_STROKE_COLOR))
                    .fillColor(Colors.fromHex(GriefPreventionConfig.MARKER_BASIC_FILL_COLOR))
                    .popupContent(processPopup(GriefPreventionConfig.MARKER_BASIC_POPUP, claim));
        }

        return builder.build();
    }

    private @NotNull String processPopup(@NotNull final String popup, @NotNull final GriefPreventionClaim claim) {
        return popup.replace("<world>", claim.getWorld().getName())
                .replace("<id>", Long.toString(claim.getID()))
                .replace("<owner>", claim.getOwnerName())
                .replace("<trusts>", getTrusts(claim))
                .replace("<area>", Integer.toString(claim.getArea()))
                .replace("<width>", Integer.toString(claim.getWidth()))
                .replace("<height>", Integer.toString(claim.getHeight()));
    }

    private @NotNull String getTrusts(@NotNull final GriefPreventionClaim claim) {
        final ArrayList<String> builders = new ArrayList<>();
        final ArrayList<String> containers = new ArrayList<>();
        final ArrayList<String> accessors = new ArrayList<>();
        final ArrayList<String> managers = new ArrayList<>();

        claim.getPermissions(builders, containers, accessors, managers);

        final StringBuilder sb = new StringBuilder();

        final String owner = claim.getOwnerName();
        final String world = claim.getWorld().getName();

        if (!builders.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_TRUST.replace("<builders>", getNames(world, owner, builders)));
        }

        if (!containers.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_CONTAINER.replace("<containers>", getNames(world, owner, containers)));
        }

        if (!accessors.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_ACCESS.replace("<accessors>", getNames(world, owner, accessors)));
        }

        if (!managers.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_PERMISSION.replace("<managers>", getNames(world, owner, managers)));
        }

        return sb.toString();
    }

    private @NotNull String getNames(@NotNull final String world, @NotNull final String owner, @NotNull final List<String> players) {
        final Map<String, Map<UUID, String>> worldCache = this.users.getOrDefault(world, new HashMap<>());

        final Map<UUID, String> cache = worldCache.getOrDefault(owner, new HashMap<>());

        cache.entrySet().removeIf(entry -> !players.contains(entry.getValue()));

        final List<String> names = new ArrayList<>();

        for (final String player : players) {
            if (player.isBlank()) continue;

            final UUID uuid = UUID.fromString(player);

            if (cache.containsKey(uuid)) {
                names.add(cache.get(uuid));

                continue;
            }

            try {
                CompletableFuture.runAsync(() -> names.add(Bukkit.getOfflinePlayer(uuid).getName()));
            } catch (final Exception exception) {
                names.add(player);
            }
        }

        this.users.put(owner, worldCache);

        return String.join(", ", names);
    }
}
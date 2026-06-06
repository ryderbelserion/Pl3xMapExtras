package com.ryderbelserion.map.hook.claims.griefprevention;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.Pl3xMapPaper;
import com.ryderbelserion.map.api.registry.PaperUserRegistry;
import com.ryderbelserion.map.api.registry.adapters.PaperUserAdapter;
import com.ryderbelserion.map.api.storage.IStorageHolder;
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

    private final IStorageHolder holder = this.platform.getStorageHolder();

    private final PaperUserRegistry userRegistry = this.platform.getUserRegistry();

    private final BasicConfig config = this.platform.getBasicConfig();

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
            world.getLayerRegistry().register(new GriefPreventionLayer(this, world));
        }
    }

    @Override
    public void unloadWorld(@NotNull final World world) {
        world.getLayerRegistry().unregister(GriefPreventionLayer.KEY);
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

        if (!builders.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_TRUST.replace("<builders>", getPlayerNames(builders)));
        }

        if (!containers.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_CONTAINER.replace("<containers>", getNames(containers)));
        }

        if (!accessors.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_ACCESS.replace("<accessors>", getNames(accessors)));
        }

        if (!managers.isEmpty()) {
            if (sb.isEmpty()) sb.append("<hr/>");

            sb.append(GriefPreventionConfig.MARKER_POPUP_PERMISSION.replace("<managers>", getPlayerNames(managers)));
        }

        return sb.toString();
    }

    private @NotNull String getNames(@NotNull final List<String> players) {
        final List<String> names = new ArrayList<>();

        for (final String player : players) {
            if (player.isBlank()) continue;

            names.add(player);
        }

        return String.join(", ", names);
    }

    private @NotNull String getPlayerNames(@NotNull final List<String> players) {
        final List<String> names = new ArrayList<>();

        for (final String player : players) {
            if (player.isBlank()) continue;

            try {
                final UUID uuid = UUID.fromString(player);

                final String name = this.userRegistry.getUser(uuid).map(PaperUserAdapter::getUsername).orElseGet(() -> {
                    if (this.userRegistry.isCached(uuid)) {
                        return this.userRegistry.getCache(uuid);
                    }

                    return this.holder.getName(uuid);
                });

                names.add(name);

                this.userRegistry.updateCache(uuid, name);
            } catch (final Exception exception) {
                names.add(player);
            }
        }

        final List<String> processed = names.stream().filter(value -> !value.isBlank() || !value.equalsIgnoreCase("N/A")).toList();

        return String.join(", ", processed);
    }
}
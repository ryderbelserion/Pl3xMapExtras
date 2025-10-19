package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.utils.ItemUtils;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.api.commands.PaperSource;
import com.ryderbelserion.map.banners.PaperBannerListener;
import com.ryderbelserion.map.banners.objects.BannerLocation;
import com.ryderbelserion.map.commands.player.ISource;
import com.ryderbelserion.map.commands.subs.CoreCommand;
import com.ryderbelserion.map.enums.Mode;
import com.ryderbelserion.map.objects.MapParticle;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class Pl3xMapPlugin extends Pl3xMapCommon {

    private final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    private final Server server = this.plugin.getServer();

    private final PluginManager pluginManager = this.server.getPluginManager();

    private final FusionPaper fusion;

    public Pl3xMapPlugin(@NotNull final FusionPaper fusion) {
        super(fusion);

        this.fusion = fusion;
    }

    @Override
    public void init(@NotNull final Audience audience) {
        super.init(audience);

        if (getBannerConfig().isEnabled()) {
            this.pluginManager.registerEvents(new PaperBannerListener(this), this.plugin);
        } else {
            this.fusion.log("warn", "The banner listener for Paper is not enabled.");
        }
    }

    @Override
    public void registerPermission(@NotNull final Mode mode, @NotNull final String parent, @NotNull final String description, @NotNull final Map<String, Boolean> children) {
        PermissionDefault permissionDefault;

        switch (mode) {
            case NOT_OP -> permissionDefault = PermissionDefault.NOT_OP;
            case TRUE -> permissionDefault = PermissionDefault.TRUE;
            case FALSE -> permissionDefault = PermissionDefault.FALSE;
            default -> permissionDefault = PermissionDefault.OP;
        }

        final PluginManager pluginManager = this.server.getPluginManager();

        if (pluginManager.getPermission(parent) != null) return;

        final Permission permission = new Permission(
                parent,
                description,
                permissionDefault,
                children
        );

        pluginManager.addPermission(permission);
    }

    @Override
    public void broadcast(@NotNull final Component component, @NotNull final String permission) {
        if (permission.isEmpty()) {
            this.server.broadcast(component);

            return;
        }

        this.server.broadcast(component, permission);
    }

    @Override
    public final boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission) {
        final CommandSender sender = (CommandSender) audience;

        return sender.hasPermission(permission);
    }

    @Override
    public final boolean isConsoleSender(@NotNull final Audience audience) {
        return audience instanceof ConsoleCommandSender;
    }

    @Override
    public void playParticle(@NotNull final BannerLocation bannerLocation, @NotNull final MapParticle mapParticle) {
        final Particle particle = ItemUtils.getParticleType(mapParticle.particle());

        if (particle == null) {
            return;
        }

        final String worldName = bannerLocation.worldName();

        final World world = this.server.getWorld(worldName);

        if (world == null) {
            this.fusion.log("warn", "The world with the name %s does not exist".formatted(worldName));

            return;
        }

        final int x = bannerLocation.x();
        final int y = bannerLocation.y();
        final int z = bannerLocation.z();

        final ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < 20; ++i) {
            world.spawnParticle(particle, x + random.nextGaussian(), y + random.nextGaussian(), z + random.nextGaussian(), 1, 0, 0, 0, 0, null, true);
        }
    }

    @Override
    public void registerCommands() {
        final LifecycleEventManager<@NotNull Plugin> manager = this.plugin.getLifecycleManager();

        manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands registry = event.registrar();

            registry.register(new CoreCommand<>("pl3xmapextras.access", "pl3xmapextras", function()).build(),
                    "The base command for Pl3xMapExtras!",
                    Collections.singletonList("pme"));
        });
    }

    public Function<CommandSourceStack, ISource> function() {
        return stack -> new PaperSource(stack.getSender());
    }

    @Override
    public void reload() {
        super.reload();
    }

    @Override
    public void stop() {
        super.stop();

        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);
    }
}
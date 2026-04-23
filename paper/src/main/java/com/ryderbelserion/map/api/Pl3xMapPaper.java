package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.core.api.interfaces.permissions.enums.Mode;
import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.utils.ItemUtils;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.commands.PaperSource;
import com.ryderbelserion.map.api.enums.Permissions;
import com.ryderbelserion.map.api.registry.PaperContextRegistry;
import com.ryderbelserion.map.api.registry.PaperMessageRegistry;
import com.ryderbelserion.map.api.registry.PaperUserRegistry;
import com.ryderbelserion.map.api.registry.adapters.PaperSenderAdapter;
import com.ryderbelserion.map.common.commands.player.ISource;
import com.ryderbelserion.map.common.commands.subs.CoreCommand;
import com.ryderbelserion.map.common.objects.MapParticle;
import com.ryderbelserion.map.common.objects.MapPosition;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.listener.banners.PaperBannerListener;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.util.ModuleUtil;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.kyori.adventure.audience.Audience;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;

public class Pl3xMapPaper extends Pl3xMapPlugin {

    private final Pl3xMapExtras plugin = Pl3xMapExtras.getPlugin();
    private final Server server = this.plugin.getServer();
    private final PluginManager pluginManager = this.server.getPluginManager();
    private final FusionPaper fusion;

    private PaperMessageRegistry messageRegistry;
    private PaperContextRegistry contextRegistry;
    private PaperSenderAdapter userAdapter;
    private PaperUserRegistry userRegistry;

    private MobsManager mobsManager;

    public Pl3xMapPaper(@NotNull final File file, @NotNull final Pl3xMapExtras plugin) {
        super(this.fusion = new FusionPaper(file, plugin));
    }

    @Override
    public void init() {
        super.init();

        this.contextRegistry = new PaperContextRegistry();

        this.userRegistry = new PaperUserRegistry();
        this.userRegistry.init();

        this.messageRegistry = new PaperMessageRegistry();
        this.messageRegistry.init();

        this.userAdapter = new PaperSenderAdapter(this);

        post();
    }

    @Override
    public void post() {
        super.post();

        registerCommands();

        // Register the permissions.
        Arrays.stream(Permissions.values()).toList().forEach(permission -> this.pluginManager.addPermission(new Permission(
                permission.getPermission(),
                permission.getDescription(),
                permission.isDefault()
        )));

        if (getBannerConfig().isEnabled()) {
            this.pluginManager.registerEvents(new PaperBannerListener(), this.plugin);
        } else {
            this.fusion.log("warn", "The banner listener for Paper is not enabled.");
        }

        // Find all plugin hooks and load them.
        ModuleUtil.findHooks();

        // Create mob manager class.
        this.mobsManager = new MobsManager();

        // Toggle all our shit on startup.
        ModuleUtil.toggleAll(false);
    }

    @Override
    public void reload() {
        super.reload();

        ModuleUtil.toggleAll(false);
    }

    @Override
    public void shutdown() {
        this.server.getGlobalRegionScheduler().cancelTasks(this.plugin);
        this.server.getAsyncScheduler().cancelTasks(this.plugin);

        // Unregister data.
        ModuleUtil.unload();

        // Clean up all our shit on shutdown.
        ModuleUtil.toggleAll(true);

        // Clear plugin hooks.
        Hook.clear();
    }

    @Override
    public void playParticle(@NotNull final MapPosition position, @NotNull final MapParticle mapParticle) {
        final Particle particle = ItemUtils.getParticleType(mapParticle.particle());

        if (particle == null) {
            return;
        }

        final String worldName = position.worldName();

        final World world = this.server.getWorld(worldName);

        if (world == null) {
            this.fusion.log("warn", "The world with the name %s does not exist".formatted(worldName));

            return;
        }

        final int x = position.x();
        final int y = position.y();
        final int z = position.z();

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
    public final boolean hasPermission(@NotNull final Audience audience, @NotNull final String permission) {
        final CommandSender sender = (CommandSender) audience;

        return sender.hasPermission(permission);
    }

    @Override
    public @NotNull final PaperContextRegistry getContextRegistry() {
        return this.contextRegistry;
    }

    @Override
    public @NotNull final PaperMessageRegistry getMessageRegistry() {
        return this.messageRegistry;
    }

    @Override
    public @NotNull final PaperSenderAdapter getSenderAdapter() {
        return this.userAdapter;
    }

    @Override
    public @NotNull final PaperUserRegistry getUserRegistry() {
        return this.userRegistry;
    }

    @Override
    public @NotNull final FusionPaper getFusion() {
        return this.fusion;
    }

    public @NotNull final Optional<MobsManager> getMobsManager() {
        return Optional.of(this.mobsManager);
    }

    public @NotNull final Function<CommandSourceStack, ISource> function() {
        return stack -> new PaperSource(stack.getSender());
    }
}
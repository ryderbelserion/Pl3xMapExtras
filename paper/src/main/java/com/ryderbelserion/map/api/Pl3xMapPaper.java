package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.utils.ItemUtils;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.Pl3xMapPlugin;
import com.ryderbelserion.map.api.registry.PaperContextRegistry;
import com.ryderbelserion.map.api.registry.PaperMessageRegistry;
import com.ryderbelserion.map.api.registry.PaperUserRegistry;
import com.ryderbelserion.map.api.registry.adapters.PaperSenderAdapter;
import com.ryderbelserion.map.common.configs.ConfigManager;
import com.ryderbelserion.map.common.configs.types.BasicConfig;
import com.ryderbelserion.map.common.objects.MapParticle;
import com.ryderbelserion.map.common.objects.MapPosition;
import com.ryderbelserion.map.hook.Hook;
import com.ryderbelserion.map.listener.banners.PaperBannerListener;
import com.ryderbelserion.map.marker.mobs.MobsManager;
import com.ryderbelserion.map.util.ModuleUtil;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
}
package com.ryderbelserion.map.api;

import com.ryderbelserion.fusion.paper.FusionPaper;
import com.ryderbelserion.fusion.paper.utils.ItemUtils;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapExtras;
import com.ryderbelserion.map.banners.PaperBannerListener;
import com.ryderbelserion.map.banners.objects.BannerLocation;
import com.ryderbelserion.map.objects.MapParticle;
import org.bukkit.Particle;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.concurrent.ThreadLocalRandom;

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
    public void init() {
        super.init();

        if (getBannerConfig().isEnabled()) {
            this.pluginManager.registerEvents(new PaperBannerListener(this), this.plugin);
        } else {
            this.fusion.log("warn", "The banner listener for Paper is not enabled.");
        }
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
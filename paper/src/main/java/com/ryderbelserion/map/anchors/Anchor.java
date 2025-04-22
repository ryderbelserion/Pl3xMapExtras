package com.ryderbelserion.map.anchors;

import com.ryderbelserion.fusion.core.api.interfaces.IPlugin;
import com.ryderbelserion.map.Pl3xMapExtras;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.world.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import java.util.Collection;
import java.util.Optional;

public abstract class Anchor implements Processor<World, Optional<Collection<Marker<?>>>>, IPlugin {

    protected final Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    public abstract void registerWorld(@NotNull final World world);

    public abstract void deleteWorld(@NotNull final World world);

    @Override
    public abstract Optional<Collection<Marker<?>>> process(@NotNull final World variable);

}
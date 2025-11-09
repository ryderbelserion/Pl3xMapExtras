package com.ryderbelserion.map.modules.mobs.interfaces;

import com.ryderbelserion.map.modules.mobs.objects.Mob;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public interface IMobLayer {

    void displayMob(@NotNull final Mob mob);

    void removeMob(@NotNull final String worldName, @NotNull final UUID uuid);

    void purgeCache();

}
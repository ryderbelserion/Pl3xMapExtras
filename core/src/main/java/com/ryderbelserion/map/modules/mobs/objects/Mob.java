package com.ryderbelserion.map.modules.mobs.objects;

import com.ryderbelserion.map.objects.MapPosition;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public record Mob(@NotNull MobTexture texture, @NotNull Component mobName, @NotNull UUID mobId, @NotNull MapPosition position) {

    public String asPlainText() {
        return PlainTextComponentSerializer.plainText().serialize(mobName);
    }
}
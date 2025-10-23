package com.ryderbelserion.map.objects;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import org.jetbrains.annotations.NotNull;

public record MapSound(@NotNull String sound, @NotNull String source, double volume, double pitch) {

    public Sound asSound() {
        return Sound.sound(Key.key(sound), Sound.Source.NAMES.valueOr(source, Sound.Source.MASTER), (float) volume, (float) pitch);
    }
}
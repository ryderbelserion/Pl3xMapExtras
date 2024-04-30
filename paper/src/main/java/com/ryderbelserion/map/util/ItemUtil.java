package com.ryderbelserion.map.util;

import com.ryderbelserion.map.Pl3xMapExtras;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.plugin.java.JavaPlugin;

public class ItemUtil {

    private final static Pl3xMapExtras plugin = JavaPlugin.getPlugin(Pl3xMapExtras.class);

    public static Particle getParticleType(String value) {
        try {
            return Registry.PARTICLE_TYPE.get(getKey(value));
        } catch (Exception exception) {
            plugin.getLogger().severe(value + " is an invalid particle type.");

            return null;
        }
    }

    public static Sound getSound(String value) {
        try {
            return Registry.SOUNDS.get(getKey(value));
        } catch (Exception exception) {
            plugin.getLogger().severe(value + " is an invalid sound type.");

            return null;
        }
    }

    private static NamespacedKey getKey(String value) {
        return NamespacedKey.minecraft(value);
    }
}
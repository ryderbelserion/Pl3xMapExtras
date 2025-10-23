package com.ryderbelserion.map.utils;

import com.ryderbelserion.fusion.core.api.exceptions.FusionException;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.util.List;

public class ConfigUtils {

    public static List<String> getStringList(@NotNull final BasicConfigurationNode node, @NotNull final List<String> defaultValue) {
        try {
            final List<String> list = node.getList(String.class);

            if (list != null) {
                return list;
            }

            return defaultValue;
        } catch (final SerializationException exception) {
            throw new FusionException(String.format("Failed to serialize %s!", node.path()), exception);
        }
    }

    public static List<String> getStringList(@NotNull final BasicConfigurationNode node) {
        return getStringList(node, List.of());
    }
}
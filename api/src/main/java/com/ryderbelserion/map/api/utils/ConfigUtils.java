package com.ryderbelserion.map.api.utils;

import com.ryderbelserion.fusion.files.FileException;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.BasicConfigurationNode;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import java.util.List;

public class ConfigUtils {

    public static @NotNull List<String> getStringList(@NotNull CommentedConfigurationNode node, @NotNull List<String> defaultValues) {
        try {
            List<String> list = node.getList(String.class);

            return list != null ? list : defaultValues;
        } catch (SerializationException exception) {
            throw new FileException(String.format("Failed to serialize %s!", node.path()), exception);
        }
    }

    public static @NotNull List<String> getStringList(@NotNull CommentedConfigurationNode node, @NotNull String defaultValue) {
        return getStringList(node, List.of(defaultValue));
    }

    public static @NotNull List<String> getStringList(@NotNull CommentedConfigurationNode node) {
        return getStringList(node, List.of());
    }

    public static @NotNull List<String> getStringList(@NotNull BasicConfigurationNode node, @NotNull List<String> defaultValues) {
        try {
            List<String> list = node.getList(String.class);

            return list != null ? list : defaultValues;
        } catch (SerializationException exception) {
            throw new FileException(String.format("Failed to serialize %s!", node.path()), exception);
        }
    }

    public static @NotNull List<String> getStringList(@NotNull BasicConfigurationNode node, @NotNull String defaultValue) {
        return getStringList(node, List.of(defaultValue));
    }

    public static @NotNull List<String> getStringList(@NotNull BasicConfigurationNode node) {
        return getStringList(node, List.of());
    }
}
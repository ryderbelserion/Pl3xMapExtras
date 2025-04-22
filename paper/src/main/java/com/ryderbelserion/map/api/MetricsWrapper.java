package com.ryderbelserion.map.api;

import com.ryderbelserion.map.Pl3xMapExtras;
import org.bstats.bukkit.Metrics;
import org.jetbrains.annotations.NotNull;

public class MetricsWrapper {

    private final Metrics metrics;

    public MetricsWrapper(@NotNull final Pl3xMapExtras plugin, final int serviceId) {
        this.metrics = new Metrics(plugin, serviceId);
    }
}
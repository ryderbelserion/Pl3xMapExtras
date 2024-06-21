package com.ryderbelserion.map.api;

import com.ryderbelserion.map.Pl3xMapExtras;

public class MetricsWrapper extends CustomMetrics {

    /**
     * Creates a new Metrics instance.
     *
     * @param serviceId The id of the service. It can be found at <a href="https://bstats.org/what-is-my-plugin-id">What is my plugin id?</a>
     */
    public MetricsWrapper(Pl3xMapExtras plugin, int serviceId) {
        super(plugin, serviceId);
    }

    public void start() {
        // If it's not enabled, we do nothing!
        if (!isEnabled()) return;
    }
}
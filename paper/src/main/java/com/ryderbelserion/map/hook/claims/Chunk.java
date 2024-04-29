package com.ryderbelserion.map.hook.claims;

public interface Chunk extends Region {

    @Override
    default int maxX() {
        return minX() + 16;
    }

    @Override
    default int maxZ() {
        return minX() + 16;
    }
}
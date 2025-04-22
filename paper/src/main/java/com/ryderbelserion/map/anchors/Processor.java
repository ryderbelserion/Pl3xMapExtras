package com.ryderbelserion.map.anchors;

public interface Processor<V, D> {

    D process(V variable);

}
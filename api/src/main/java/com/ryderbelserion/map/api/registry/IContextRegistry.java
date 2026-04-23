package com.ryderbelserion.map.api.registry;

import org.jetbrains.annotations.NotNull;
import java.util.UUID;

public interface IContextRegistry<P> {

    UUID getUUID(@NotNull final P player);

}
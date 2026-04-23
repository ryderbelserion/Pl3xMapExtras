package com.ryderbelserion.map.api.adapters;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface IMessageAdapter {

    String getValue(@NotNull final Audience audience);

    String getValue();

}
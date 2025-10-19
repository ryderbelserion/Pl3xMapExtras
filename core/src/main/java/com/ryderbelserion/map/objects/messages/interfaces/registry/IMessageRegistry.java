package com.ryderbelserion.map.objects.messages.interfaces.registry;

import com.ryderbelserion.map.objects.messages.interfaces.IMessage;
import net.kyori.adventure.key.Key;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public interface IMessageRegistry {

    void addMessage(@NotNull final Key locale, @NotNull final Key key, @NotNull final IMessage message);

    void removeMessage(@NotNull final Key key);

    IMessage getMessage(@NotNull final Key locale, @NotNull final Key key);

    IMessage getMessage(@NotNull final Key key);

    @ApiStatus.Internal
    void init();

}
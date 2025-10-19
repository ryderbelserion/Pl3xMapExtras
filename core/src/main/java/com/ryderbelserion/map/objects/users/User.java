package com.ryderbelserion.map.objects.users;

import com.ryderbelserion.fusion.core.FusionCore;
import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapProvider;
import com.ryderbelserion.map.constants.Messages;
import com.ryderbelserion.map.objects.users.interfaces.IUser;
import com.ryderbelserion.map.registry.MessageRegistry;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import java.util.Map;

public class User extends IUser {

    private final Pl3xMapCommon plugin = Pl3xMapProvider.getInstance();

    private final MessageRegistry messageRegistry = this.plugin.getMessageRegistry();

    private final FusionCore fusion = this.plugin.getFusion();

    private final Audience audience;

    public User(@NotNull final Audience audience) {
        this.audience = audience;
    }

    private Key locale = Messages.default_locale;

    @Override
    public Component getComponent(@NotNull final Key key, @NotNull final Map<String, String> placeholders) {
        return this.messageRegistry.getMessage(getLocale(), key).getComponent(getAudience(), placeholders);
    }

    @Override
    public void sendMessage(@NotNull final Key key, @NotNull final Map<String, String> placeholders) {
        this.messageRegistry.getMessage(getLocale(), key).send(getAudience(), placeholders);
    }

    @Override
    public final boolean hasPermission(@NotNull final String permission) {
        return this.plugin.hasPermission(getAudience(), permission);
    }

    @Override
    public void setLocale(@NotNull final Locale locale) {
        final String country = locale.getCountry();
        final String language = locale.getLanguage();

        final String value = "%s_%s.yml".formatted(language, country).toLowerCase();

        if (!value.equalsIgnoreCase("en_us.yml")) {
            this.locale = Key.key(Pl3xMapCommon.namespace, value);
        }

        this.fusion.log("warn", "Locale Debug: Country: {}, Language: {}", country, language);
    }

    @Override
    public @NotNull final Audience getAudience() {
        return this.audience;
    }

    @Override
    public @NotNull final Key getLocale() {
        return this.locale;
    }
}
package com.ryderbelserion.map.modules.mobs.objects;

import com.ryderbelserion.map.Pl3xMapCommon;
import com.ryderbelserion.map.Pl3xMapProvider;
import com.ryderbelserion.map.enums.constants.Namespaces;
import com.ryderbelserion.map.modules.mobs.config.MobConfig;
import com.ryderbelserion.map.objects.MapPosition;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import net.pl3x.map.core.markers.marker.Icon;
import net.pl3x.map.core.markers.marker.Marker;
import net.pl3x.map.core.markers.option.Options;
import net.pl3x.map.core.markers.option.Tooltip;
import org.jetbrains.annotations.NotNull;
import java.util.Optional;
import java.util.UUID;

public class Mob {

    private final Pl3xMapCommon plugin = Pl3xMapProvider.getInstance();

    private final Component displayName;
    private final String plainText;
    private final UUID uuid;
    private final MapPosition position;
    private final MobTexture texture;

    public Mob(@NotNull final Component displayName, @NotNull final UUID uuid, @NotNull final MapPosition position, @NotNull final MobTexture texture) {
        this.plainText = PlainTextComponentSerializer.plainText().serialize(this.displayName = displayName);
        this.uuid = uuid;
        this.position = position;
        this.texture = texture;
    }

    public @NotNull final Optional<Icon> getIcon() {
        final MobConfig config = this.plugin.getMobConfig();

        final Icon icon = Marker.icon("%s_%s_%s".formatted(Namespaces.mob_key, this.position.worldName(), this.uuid),
                this.position.asPoint(), this.texture.getKey(), config.getIconVector());

        final Options.Builder builder = new Options.Builder();

        builder.tooltipDirection(Tooltip.Direction.TOP).tooltipContent(config.getPopupContent().replace("<name>", this.plainText));

        return Optional.of(icon.setOptions(builder.build()));
    }

    public @NotNull final Component getDisplayName() {
        return this.displayName;
    }

    public @NotNull final String getPlainText() {
        return this.plainText;
    }

    public @NotNull final MapPosition getPosition() {
        return this.position;
    }

    public @NotNull final MobTexture getTexture() {
        return this.texture;
    }

    public @NotNull final UUID getUuid() {
        return this.uuid;
    }
}
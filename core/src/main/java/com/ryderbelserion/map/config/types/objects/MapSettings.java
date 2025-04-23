package com.ryderbelserion.map.config.types.objects;

import ch.jalu.configme.Comment;
import ch.jalu.configme.beanmapper.ExportName;
import org.jetbrains.annotations.NotNull;

public class MapSettings {

    @ExportName("label")
    @Comment("The name of the layer displayed on the website.")
    private String label;

    @ExportName("show-controls")
    @Comment("Shows controls for this map layer.")
    private boolean show_button = false;

    @ExportName("default-hidden")
    @Comment("Whether the map layer is hidden by default.")
    private boolean hidden = false;

    @ExportName("update-interval")
    @Comment("The update interval for this map layer.")
    private int interval = 5;

    @ExportName("priority")
    @Comment("The priority for this map layer.")
    private int priority = 1;

    @ExportName("z-index")
    @Comment("The z-index for this map layer.")
    private int index = 1;

    @ExportName("css")
    @Comment("The custom css for this map layer.")
    private String css = "";

    //@ExportName("interaction.place")
    //@Comment("Should banners be displayed when you place the block?")
    //private boolean create = true;

    //todo() add sound property

    public MapSettings(@NotNull final String label) {
        this.label = label;
    }

    public void setLabel(@NotNull final String label) {
        this.label = label;
    }

    public void showButton(final boolean show_button) {
        this.show_button = show_button;
    }

    public void setHidden(final boolean hidden) {
        this.hidden = hidden;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public void setCss(@NotNull final String css) {
        this.css = css;
    }

    public @NotNull final String getLabel() {
        return this.label;
    }

    public final boolean isButtonShown() {
        return this.show_button;
    }

    public final boolean isLayerHidden() {
        return this.hidden;
    }

    public final int getIndex() {
        return this.index;
    }

    public final int getInterval() {
        return this.interval;
    }

    public final int getPriority() {
        return this.priority;
    }

    public @NotNull final String getCss() {
        return this.css;
    }
}
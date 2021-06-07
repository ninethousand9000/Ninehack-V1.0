package me.ninethousand.ninehack.event.events;

import me.ninethousand.ninehack.event.EventStage;

public class Render3DEvent extends EventStage {
    private float partialTicks;

    public Render3DEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return this.partialTicks;
    }
}

package me.ninethousand.ninehack.event.events;

public final class HudRenderEvent {
    private final float partialTicks;

    public HudRenderEvent(final float partialTicks) {
        this.partialTicks = partialTicks;
    }

    public float getPartialTicks() {
        return partialTicks;
    }
}

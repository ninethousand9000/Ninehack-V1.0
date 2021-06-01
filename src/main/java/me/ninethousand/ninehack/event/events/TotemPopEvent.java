package me.ninethousand.ninehack.event.events;

import com.olliem5.pace.event.Event;
import net.minecraft.entity.Entity;

public final class TotemPopEvent extends Event {
    public final Entity entity;

    public TotemPopEvent(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }
}

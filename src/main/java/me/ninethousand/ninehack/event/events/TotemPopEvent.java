package me.ninethousand.ninehack.event.events;

import me.ninethousand.ninehack.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public class TotemPopEvent extends EventStage {
    private final EntityPlayer entity;

    public TotemPopEvent(EntityPlayer entity) {
        this.entity = entity;
    }

    public EntityPlayer getEntity() {
        return this.entity;
    }
}

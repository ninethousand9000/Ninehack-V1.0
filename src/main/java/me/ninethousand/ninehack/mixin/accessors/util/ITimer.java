package me.ninethousand.ninehack.mixin.accessors.util;

import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Timer.class)
public interface ITimer {
    @Accessor("tickLength")
    void setTickLength(final float tickLength);
}

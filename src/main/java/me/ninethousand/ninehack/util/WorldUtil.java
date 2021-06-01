package me.ninethousand.ninehack.util;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.mixin.accessors.util.ITimer;
import net.minecraft.item.ItemStack;

public class WorldUtil implements NineHack.Globals {
    public static void setTickLength(final float tickLength) {
        ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(tickLength);
    }
}

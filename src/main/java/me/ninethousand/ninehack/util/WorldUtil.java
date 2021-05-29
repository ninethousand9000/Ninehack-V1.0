package me.ninethousand.ninehack.util;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.mixin.accessors.game.IItemRenderer;
import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.mixin.accessors.util.ITimer;
import net.minecraft.item.ItemStack;

public class WorldUtil implements NineHack.Globals {
    public static void setTickLength(final float tickLength) {
        ((ITimer) ((IMinecraft) mc).getTimer()).setTickLength(tickLength);
    }

    public static void setEquippedProgressMainhand(final float progressMainhand) {
        ((IItemRenderer) mc).setEquippedProgressMainhand(progressMainhand);
    }

    public static void setitemStackMainHand(ItemStack itemStack) {
        ((IItemRenderer) mc).setItemStackMainHand(itemStack);
    }
}

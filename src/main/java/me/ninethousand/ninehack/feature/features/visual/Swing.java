package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.util.WorldUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;

@NineHackFeature(name = "Swing", description = "Swings with hand", category = Category.Visual)
public class Swing extends Feature {
    public static Feature INSTANCE;

    public static Setting<Hand> hand = new Setting<>("Hand", Hand.OFFHAND);

    public Swing() {
        addSettings(hand);
    }

    @Override
    public void onUpdate() {
        if (mc.world == null)
            return;
        if (hand.getValue().equals(Hand.OFFHAND)) {
            mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (hand.getValue().equals(Hand.MAINHAND)) {
            mc.player.swingingHand = EnumHand.MAIN_HAND;
        }
    }

    public enum Hand {
        OFFHAND,
        MAINHAND,
    }

}

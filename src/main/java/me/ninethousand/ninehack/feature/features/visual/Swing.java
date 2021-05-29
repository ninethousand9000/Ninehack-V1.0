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
    public static final Feature INSTANCE = new Swing();
    public static Setting<Hand> hand = new Setting<>("Hand", Hand.PACKETSWING);

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
        if (hand.getValue() == Hand.PACKETSWING) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof ItemSword) {
                WorldUtil.setEquippedProgressMainhand(50f);
                WorldUtil.setitemStackMainHand(mc.player.getHeldItemMainhand());
            }
        }
    }

    public enum Hand {
        OFFHAND,
        MAINHAND,
        PACKETSWING
    }

}

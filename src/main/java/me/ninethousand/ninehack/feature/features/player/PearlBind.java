package me.ninethousand.ninehack.feature.features.player;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.security.Key;

@NineHackFeature(name = "Pearl Bind", description = "Throw pearl with keybind", category = Category.Player)
public class PearlBind extends Feature {
    public static Feature INSTANCE;

    public static boolean clicked = false;

    @Override
    public void onEnable() {
        if (nullCheck()) disable();

        throwPearl();
        disable();
    }

    private void throwPearl() {
        boolean offhand;
        int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean bl = offhand = mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        if (pearlSlot != -1 || offhand) {
            int oldslot = mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            mc.playerController.processRightClick(mc.player, mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }
}

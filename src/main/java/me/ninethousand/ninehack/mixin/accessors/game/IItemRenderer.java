package me.ninethousand.ninehack.mixin.accessors.game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ItemRenderer.class)
public interface IItemRenderer {
    @Accessor("equippedProgressMainHand")
    public float getEquippedProgressMainhand();

    @Accessor("equippedProgressMainHand")
    void setEquippedProgressMainhand(final float progressMainhand);

    @Accessor("itemStackMainHand")
    void setItemStackMainHand(ItemStack itemStackMainHand);
}

package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.mixin.accessors.game.IBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Block.class)
public class MixinBlock implements IBlock {
    @Shadow
    protected Material material;

    @Override
    public Material getMaterial() {
        return material;
    }
}

package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.mixin.accessors.game.IEntity;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public class MixinEntity implements IEntity {
    @Shadow
    protected boolean isInWeb;

    @Override
    public boolean getIsInWeb() {
        return isInWeb;
    }

    @Override
    public void setIsInWeb(boolean isInWeb) {
        this.isInWeb = isInWeb;
    }
}

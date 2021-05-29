package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.util.Stopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Minecraft.class)
public final class MixinMinecraft implements IMinecraft {
    @Shadow
    private int rightClickDelayTimer;
    @Shadow
    @Final
    private Timer timer;

    @Shadow
    public EntityPlayerSP player;

    @Override
    public int getRightClickDelayTimer() {
        return rightClickDelayTimer;
    }

    @Override
    public void setRightClickDelayTimer(final int rightClickDelayTimer) {
        this.rightClickDelayTimer = rightClickDelayTimer;
    }

    @Override
    public Timer getTimer() {
        return timer;
    }

    private final Stopper stopper = new Stopper();
    private int progression = 0;
    private boolean progressionState = true;
}

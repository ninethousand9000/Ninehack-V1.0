package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.feature.features.visual.Menu;
import me.ninethousand.ninehack.feature.gui.menu.CustomMainMenu;
import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.util.Stopper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Timer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public final class MixinMinecraft implements IMinecraft {
    @Shadow
    private int rightClickDelayTimer;

    @Shadow
    private boolean integratedServerIsRunning;

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

    @Override
    public boolean getIntegratedServerIsRunning() {
        return integratedServerIsRunning;
    }

    private final Stopper stopper = new Stopper();
    private int progression = 0;
    private boolean progressionState = true;

    @Inject(method={"runTick()V"}, at={@At(value="RETURN")})
    private void runTick(CallbackInfo callbackInfo) {
        if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu && Menu.INSTANCE.isEnabled()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new CustomMainMenu());
        }
    }

    @Inject(method={"displayGuiScreen"}, at={@At(value="HEAD")})
    private void displayGuiScreen(GuiScreen screen, CallbackInfo ci) {
        if (screen instanceof GuiMainMenu && Menu.INSTANCE.isEnabled()) {
            this.displayGuiScreen(new CustomMainMenu());
        }
    }

    private void displayGuiScreen(CustomMainMenu customMainMenu) {
        customMainMenu.initGui();
    }
}

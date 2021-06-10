package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.CustomFont;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FontRenderer.class)
public final class MixinFontRenderer {
    private final Minecraft mc = Minecraft.getMinecraft();

    @Inject(method={"drawString(Ljava/lang/String;FFIZ)I"}, at={@At(value="HEAD")}, cancellable=true)
    public void renderStringHook(String text, float x, float y, int color, boolean dropShadow, CallbackInfoReturnable<Integer> info) {
        if (mc.world != null && CustomFont.INSTANCE.isEnabled() && CustomFont.override.getValue() && NineHack.TEXT_MANAGER != null) {
            float result = NineHack.TEXT_MANAGER.drawString(text, x, y, color, dropShadow);
            info.setReturnValue((int)result);
        }
    }
}

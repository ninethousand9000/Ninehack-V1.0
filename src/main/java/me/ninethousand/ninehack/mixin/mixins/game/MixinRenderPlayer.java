package me.ninethousand.ninehack.mixin.mixins.game;

import me.ninethousand.ninehack.feature.features.visual.FutureChams;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ RenderPlayer.class })

public class MixinRenderPlayer {
    /*@Inject(method = "renderEntityName", at = @At("HEAD"), cancellable = true)
    public void renderEntityNameHook(AbstractClientPlayer entityIn, double x, double y, double z, String name, double distanceSq, CallbackInfo info) {
        if (OyVey.moduleManager.isModuleEnabled("NameTags"))
            info.cancel();
    }*/

    @Overwrite
    public ResourceLocation getEntityTexture(final AbstractClientPlayer entity) {
        if (FutureChams.INSTANCE.isEnabled()) {
            GL11.glColor4f(FutureChams.chamColor.getValue().getRed() / 255.0f, FutureChams.chamColor.getValue().getGreen() / 255.0f, FutureChams.chamColor.getValue().getBlue() / 255.0f, FutureChams.chamColor.getValue().getAlpha() / 255.0f);
//            return new ResourceLocation("minecraft:steve_skin1.png");
            return new ResourceLocation("textures/misc/enchanted_item_glint.png");
        }
        return entity.getLocationSkin();
    }

}

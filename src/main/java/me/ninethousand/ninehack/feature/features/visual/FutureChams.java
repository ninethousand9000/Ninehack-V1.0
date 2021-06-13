package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.event.events.RenderEntityEvent;
import me.ninethousand.ninehack.event.events.RenderEntityModelEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glDepthRange;

@NineHackFeature(name = "Future Chams", description = "Future 3.21 beta", category = Category.Visual)
public class FutureChams extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Color> chamColor = new Setting<>("Color", new Color(168, 0, 232, 150));
    public static final Setting<Boolean> players = new Setting<>("Players", true);
    public static final Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static final Setting<Boolean> mobs = new Setting<>("Mobs", true);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 40, 100, 1);


    public FutureChams() {
        addSettings(
                chamColor,
                players,
                crystals,
                mobs,
                range
        );
    }

    @SubscribeEvent
    public void onRenderPre(RenderEntityEvent.Head event) {
        if (event.getType() == RenderEntityEvent.Type.COLOR) {
            return;
        }

        if (mc.player == null || mc.world == null) {
            return;
        }

        Entity entity1 = event.getEntity();

        if (entity1.getDistance(mc.player) > range.getValue()) {
            return;
        }

        if (players.getValue() && entity1 instanceof EntityPlayer && entity1 != mc.player) {
            createChamsPre();
        }

        if (mobs.getValue() && (entity1 instanceof EntityCreature || entity1 instanceof EntitySlime || entity1 instanceof EntitySquid)) {
            createChamsPre();
        }

        if (crystals.getValue() && entity1 instanceof EntityEnderCrystal) {
            createChamsPre();
        }
    }

    @SubscribeEvent
    public void onRenderPost(RenderEntityEvent.Return event) {
        if (event.getType() == RenderEntityEvent.Type.COLOR) {
            return;
        }

        if (mc.player == null || mc.world == null) {
            return;
        }

        Entity entity1 = event.getEntity();

        if (entity1.getDistance(mc.player) > range.getValue()) {
            return;
        }

        if (players.getValue() && entity1 instanceof EntityPlayer && entity1 != mc.player) {
            createChamsPost();
        }

        if (mobs.getValue() && (entity1 instanceof EntityCreature || entity1 instanceof EntitySlime || entity1 instanceof EntitySquid)) {
            createChamsPost();
        }

        if (crystals.getValue() && entity1 instanceof EntityEnderCrystal) {
            createChamsPost();
        }
    }


    public static void createChamsPre() {
        mc.getRenderManager().setRenderShadow(false);
        mc.getRenderManager().setRenderOutlines(false);
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(true);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
        glEnable(GL_POLYGON_OFFSET_FILL);
        glDepthRange(0.0, 0.01);
        GlStateManager.popMatrix();
    }

    public static void createChamsPost() {
        boolean shadow = mc.getRenderManager().isRenderShadow();
        mc.getRenderManager().setRenderShadow(shadow);
        GlStateManager.pushMatrix();
        GlStateManager.depthMask(false);
        glDisable(GL_POLYGON_OFFSET_FILL);
        glDepthRange(0.0, 1.0);
        GlStateManager.popMatrix();
    }
}

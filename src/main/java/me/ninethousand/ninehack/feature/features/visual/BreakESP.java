package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.event.events.Render3DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.mixin.accessors.game.IRenderGlobal;
import me.ninethousand.ninehack.util.RenderUtil;

import java.awt.*;

@NineHackFeature(name = "BreakESP", description = "brr", category = Category.Visual)
public class BreakESP extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Color> renderColor = new Setting<>("Color", new Color(0x80298997, true));
    public static final NumberSetting<Integer> width = new NumberSetting<>("Outline Width", 1, 2, 5, 1);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 15, 20, 1);
    public static final Setting<Boolean> renderText = new Setting<>("%", true);

    public BreakESP() {
        addSettings(
                renderColor,
                width,
                range,
                renderText
        );
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (nullCheck())
            return;

        ((IRenderGlobal) mc.renderGlobal).getDamagedBlocks().forEach((integer, destroyBlockProgress) -> {
            destroyBlockProgress.getPosition();
            if (destroyBlockProgress.getPosition().getDistance((int) mc.player.posX,(int)  mc.player.posY,(int)  mc.player.posZ) <= range.getValue()) {
                RenderUtil.drawBlockOutline(destroyBlockProgress.getPosition(), renderColor.getValue(), width.getValue(), false, 0, false, false, renderColor.getValue().getAlpha());

                if (renderText.getValue()) {
                    try {
                        RenderUtil.drawNametagFromBlockPos(destroyBlockProgress.getPosition(), 0.6f, mc.world.getEntityByID(integer).getName());
                        RenderUtil.drawNametagFromBlockPos(destroyBlockProgress.getPosition(), 0.2f, (destroyBlockProgress.getPartialBlockDamage() * 10) + "%");
                    } catch (Exception ignored) {

                    }
                }
            }
        });
    }
}

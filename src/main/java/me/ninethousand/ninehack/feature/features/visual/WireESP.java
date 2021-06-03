package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.features.combat.Burrow;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.util.TextUtil;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

@NineHackFeature(name = "Chams", description = "Pretty Chams", category = Category.Visual)
public class WireESP extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Color> pColor = new Setting<>("Player", new Color(168, 0, 232, 255));
    public static final Setting<Color> cColor = new Setting<>("Crystal", new Color(168, 0, 232, 255));
    public static final NumberSetting<Float> pWidth = new NumberSetting<>("Player Width", 0.1f, 1.0f, 3.0f, 1);
    public static final NumberSetting<Float> cWidth = new NumberSetting<>("Crystal Width", 0.1f, 1.0f, 3.0f, 1);
    public static final Setting<RenderMode> pMode = new Setting<>("Player Mode", RenderMode.Solid);
    public static final Setting<RenderMode> cMode = new Setting<>("Crystal Mode", RenderMode.Solid);
    public static final Setting<Boolean> players = new Setting<>("Players", true);
    public static final Setting<Boolean> pModel = new Setting<>("Player Model", true);
    public static final Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static final Setting<Boolean> cModel = new Setting<>("Crystal Model", true);

    public WireESP() {
        addSettings(
                pColor,
                cColor,
                pWidth,
                cWidth,
                pMode,
                cMode,
                players,
                pModel,
                crystals,
                cModel
        );
    }

    @SubscribeEvent
    public void onRenderPlayerEvent(RenderPlayerEvent.Pre event) {
        event.getEntityPlayer().hurtTime = 0;
    }

    public enum RenderMode {
        Solid,
        Wire
    }
}

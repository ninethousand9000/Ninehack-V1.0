package me.ninethousand.ninehack.feature.features.movement;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.hud.Component;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.mixin.accessors.game.IEntity;
import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.mixin.accessors.util.ITimer;
import me.ninethousand.ninehack.util.ChatUtil;
import me.ninethousand.ninehack.util.WorldUtil;

@NineHackFeature(name = "AntiWeb", description = "Fall through webs fast", category = Category.Movement)
public class AntiWeb extends Feature {
    public static Feature INSTANCE;

    public static final Setting<WebMode> mode = new Setting<>("Mode", WebMode.Timer);
    public static final NumberSetting<Float> factor = new NumberSetting<>("Size", 0.3f, 1f, 5f, 0);

    public AntiWeb() {
        addSettings(mode, factor);
    }

    @Override
    public void onUpdate() {
        if (((IEntity)mc.player).getIsInWeb()) {
            switch (mode.getValue()) {
                case Timer:
                    if (!mc.player.onGround && mc.player.motionY <= 0)
                        WorldUtil.setTickLength(factor.getValue());
                    else
                        WorldUtil.setTickLength(50f);
                    break;
            }
        }
    }

    public enum WebMode {
        Vanilla,
        Normal,
        Timer
    }
}

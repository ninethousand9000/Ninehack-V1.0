package me.ninethousand.ninehack.feature.features.movement;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;
import org.lwjgl.input.Keyboard;

@NineHackFeature(name = "Sprint", description = "Be fast af", category = Category.Movement, key = Keyboard.KEY_F)
public class Sprint extends Feature {
    public static Feature INSTANCE;

    public static final Setting<SprintMode> mode = new Setting<>("Mode", SprintMode.Rage);


    public Sprint() {
        addSettings(mode);
    }

    @Override
    public void onUpdate() {
        if (nullCheck())
            return;

        if (!mc.player.isSprinting())
            mc.player.setSprinting(mode.getValue() == SprintMode.Rage || mc.gameSettings.keyBindForward.isKeyDown());

        for (Setting<?> setting : getSettings()) {
            NineHack.log(setting.getName() + " setting name.");
        }
    }

    public enum SprintMode {
        Rage,
        Normal
    }
}

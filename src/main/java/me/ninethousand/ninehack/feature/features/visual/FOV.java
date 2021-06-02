package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;

@NineHackFeature(name = "Custom FOV", description = "Change default FOV", category = Category.Visual)
public class FOV extends Feature {
    public static Feature INSTANCE;
    NumberSetting<Integer> fov = new NumberSetting<>("FOV", 30, 120, 150, 1);

    public FOV() {
        addSettings(fov);
    }

    @Override
    public void onUpdate() {
        mc.gameSettings.fovSetting = (float) fov.getValue();
    }
}

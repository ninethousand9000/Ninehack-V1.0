package me.ninethousand.ninehack.feature.features.other;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;

import java.awt.*;

@NineHackFeature(name = "Slider Test", description = "Slide in the dms", category = Category.Other)
public class SliderTest extends Feature {
    public static Feature INSTANCE;
    NumberSetting<Integer> sliderI = new NumberSetting<>("Int Slider", 0, 50, 100, 1);
    NumberSetting<Double> sliderD = new NumberSetting<>("Doub Slider", 77D, 85D, 365D, 1);
    NumberSetting<Float> sliderF = new NumberSetting<>("Doub Slider", 77f, 85f, 365f, 1);
    Setting<Color> colorSetting = new Setting<>("Outline Color", Color.CYAN);


    public SliderTest() {
        addSettings(sliderI, sliderD, sliderF, colorSetting);
    }

}

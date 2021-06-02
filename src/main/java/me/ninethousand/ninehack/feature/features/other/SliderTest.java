package me.ninethousand.ninehack.feature.features.other;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;

@NineHackFeature(name = "Slider Test", description = "Slide in the dms", category = Category.Other)
public class SliderTest extends Feature {
    public static Feature INSTANCE;
    NumberSetting<Integer> slider = new NumberSetting<>("Int Slider", 0, 50, 100, 1);
    NumberSetting<Integer> sliderX = new NumberSetting<>("Int Slider", 0, 5, 10, 1);
    NumberSetting<Integer> slider2 = new NumberSetting<>("Int Slider", 10, 50, 100, 1);
    NumberSetting<Integer> slider3 = new NumberSetting<>("Int Slider", 0, 50, 98, 1);
    NumberSetting<Integer> slider4 = new NumberSetting<>("Int Slider", 3, 50, 98, 1);
    NumberSetting<Integer> slider5 = new NumberSetting<>("Int Slider", 2, 50, 97, 1);
    NumberSetting<Integer> slider6 = new NumberSetting<>("Int Slider", 0, 50, 1000, 1);


    public SliderTest() {
        addSettings(slider, sliderX);
    }

}

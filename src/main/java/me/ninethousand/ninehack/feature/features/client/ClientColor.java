package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.util.ColorUtil;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@NineHackFeature(name = "Colors", description = "Modify the client colors", category = Category.Client)
public class ClientColor extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Color> GLOBAL_COLOR = new Setting<>("Global Color", new Color(214,214,214,255));

    public static final Setting<Boolean> rainbow = new Setting<>("Rainbow", true);
    public static final NumberSetting<Integer> rainbowSpeed = new NumberSetting<>("Speed", 0, 20, 100, 1);
    public static final NumberSetting<Integer> rainbowSat = new NumberSetting<>("Saturation", 0, 255, 255, 1);
    public static final NumberSetting<Double> rainbowBrightness = new NumberSetting<>("Brightness", 0D, 255D, 255D, 1);

    public static final NumberSetting<Integer> r = new NumberSetting<>("Red", 0, 255, 255, 1);
    public static final NumberSetting<Integer> g = new NumberSetting<>("Green", 0, 255, 255, 1);
    public static final NumberSetting<Integer> b = new NumberSetting<>("Blue", 0, 255, 255, 1);
    public static final NumberSetting<Integer> a = new NumberSetting<>("Alpha", 0, 255, 255, 1);

    public static float hue;
    public static Map<Integer, Integer> colorHeightMap = new HashMap<Integer, Integer>();

    public ClientColor() {
        addSettings(
                GLOBAL_COLOR,
                rainbow,
                rainbowSpeed,
                rainbowSat,
                rainbowBrightness,
                r,
                g,
                b,
                a
        );
    }

    @Override
    public void onUpdate() {
        int colorSpeed = 101 - this.rainbowSpeed.getValue();
        float tempHue = this.hue = (float) (System.currentTimeMillis() % (long) (360 * colorSpeed)) / (360.0f * (float) colorSpeed);
        for (int i = 0; i <= 510; ++i) {
            this.colorHeightMap.put(i, Color.HSBtoRGB(tempHue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f));
            tempHue += 0.0013071896f;
        }
    }

    public int getCurrentColorHex() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.HSBtoRGB(this.hue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f);
        }
        return ColorUtil.toARGB(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }

    public Color getCurrentColor() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.getHSBColor(this.hue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f);
        }
        return new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }

}

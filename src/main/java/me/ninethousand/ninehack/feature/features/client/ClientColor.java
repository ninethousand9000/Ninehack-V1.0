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

    public static final Setting<Boolean> colorMode = new Setting<>("HSB Mode", true);
    public static final Setting<Color> GLOBAL_COLOR = new Setting<>("Global Color", new Color(214,214,214,255));

    public static final Setting<Boolean> rainbow = new Setting<>("Rainbow", true);
    public static final NumberSetting<Integer> saturation = new NumberSetting<>("Saturation", 0, 255, 255, 1);
    public static final NumberSetting<Integer> brightness = new NumberSetting<>("Brightness", 0, 255, 255, 1);
    public static final NumberSetting<Integer> speed = new NumberSetting<>("Speed", 0, 20, 100, 1);
    public static final NumberSetting<Integer> step = new NumberSetting<>("Step", 0, 14, 30, 1);

    public float hue;
    public static Map<Integer, Integer> colorHeightMap = new HashMap<Integer, Integer>();

    public ClientColor() {
        addSettings(
                colorMode,
                GLOBAL_COLOR,
                rainbow,
                saturation,
                brightness,
                speed,
                step
        );
    }

    @Override
    public void onUpdate() {
        if (rainbow.getValue()) {
            GLOBAL_COLOR.setValue(getCurrentColor());
        }
    }

    /*@Override
    public void on2DRenderEvent(Render2DEvent event) {
        NineHack.TEXT_MANAGER.drawRainbowString("Rainbow Text", 1, 1, rainbow(100), 1, false);
    }*/

    @Override
    public void onTick() {
        int colorSpeed = (int) (101 - speed.getValue());
        float tempHue = this.hue = (float) (System.currentTimeMillis() % (long) (360 * colorSpeed)) / (360.0f * (float) colorSpeed);
        for (int i = 0; i <= 510 * 8; ++i) {
            this.colorHeightMap.put(i, Color.HSBtoRGB(tempHue, (float) saturation.getValue().intValue() / 255.0f, (float) brightness.getValue().intValue() / 255.0f));
            if (tempHue + 0.0013071896f < 1) {
                tempHue += 0.0013071896f;
            }
            else {
                tempHue = 0;
            }
        }
    }

    public int getCurrentColorHex() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.HSBtoRGB(this.hue, (float) saturation.getValue().intValue() / 255.0f, (float) brightness.getValue().intValue() / 255.0f);
        }
        return ColorUtil.toARGB(GLOBAL_COLOR.getValue().getRed(), GLOBAL_COLOR.getValue().getGreen(), GLOBAL_COLOR.getValue().getBlue(), GLOBAL_COLOR.getValue().getAlpha());
    }

    public Color getCurrentColor() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.getHSBColor(this.hue, (float) saturation.getValue().intValue() / 255.0f, (float) brightness.getValue().intValue() / 255.0f);
        }
        return new Color(GLOBAL_COLOR.getValue().getRed(), GLOBAL_COLOR.getValue().getGreen(), GLOBAL_COLOR.getValue().getBlue(), GLOBAL_COLOR.getValue().getAlpha());
    }


    /*public static int rainbow(long offset) {
        float hue = (float) ((((System.currentTimeMillis() * (speed.getValue() / 10)) + (offset * 500)) % (30000L / (difference.getValue() / 100))) / (30000.0f / (difference.getValue() / 20)));
        int rgb = Color.HSBtoRGB(hue, (float) saturation.getValue(), (float) brightness.getValue());
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        return toRGBA(red, green, blue, 255);
    }*/

    public Color staticRainbow() {
        float hue = (float) (System.currentTimeMillis() % 11520L) / 12000.0f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        return new Color(red, green, blue);
    }

    public static int toRGBA(int r, int g, int b, int a) {
        return (r << 16) + (g << 8) + (b) + (a << 24);
    }

    /*@Override
    public void onUpdate() {
        int colorSpeed = 101 - this.rainbowSpeed.getValue();
        float tempHue = this.hue = (float) (System.currentTimeMillis() % (long) (360 * colorSpeed)) / (360.0f * (float) colorSpeed);
        for (int i = 0; i <= 510; ++i) {
            this.colorHeightMap.put(i, Color.HSBtoRGB(tempHue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f));
            tempHue += 0.0013071896f;
        }
        GLOBAL_COLOR.setValue(getCurrentColor());
    }

    public int getCurrentColorHex() {
        if (this.rainbow.getValue().booleanValue()) {
            return Color.HSBtoRGB(this.hue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f);
        }
        return ColorUtil.toARGB(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }

    public Color getCurrentColor() {
        if (rainbow.getValue()) {
            return Color.getHSBColor(this.hue, (float) rainbowSat.getValue().intValue() / 255.0f, (float) this.rainbowBrightness.getValue().intValue() / 255.0f);
        }
        return new Color(r.getValue(), g.getValue(), b.getValue(), a.getValue());
    }*/

}

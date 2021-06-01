package me.ninethousand.ninehack.feature.hud;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.ClientFont;
import me.ninethousand.ninehack.feature.features.client.HUD;

import java.awt.*;

public abstract class Component {
    public abstract void drawComponent(int x, int y);

    public void drawString(String text, int x, int y, Color color, float factor, boolean shadow) {
        if (HUD.rainbow.getValue()) {
            NineHack.TEXT_MANAGER.drawRainbowString(text, x, y, staticRainbow().getRGB(), 10f, ClientFont.shadow.getValue());
        }
        else {
            NineHack.TEXT_MANAGER.drawString(text, x, y, color.getRGB(), ClientFont.shadow.getValue());
        }
    }

    public Color staticRainbow() {
        float hue = (float) (System.currentTimeMillis() % 11520L) / 12000.0f;
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        int red = rgb >> 16 & 255;
        int green = rgb >> 8 & 255;
        int blue = rgb & 255;
        return new Color(red, green, blue);
    }

    public enum Priority {
        Maximum,
        Medium,
        Low
    }

    public enum Corner {
        TopLeft,
        TopRight,
        BottomLeft,
        BottomRight
    }
}

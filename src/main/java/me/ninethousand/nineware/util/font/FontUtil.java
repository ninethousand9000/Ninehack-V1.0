package me.ninethousand.nineware.util.font;

import me.ninethousand.nineware.NineWare;
import net.minecraft.client.Minecraft;

import java.awt.*;

public final class FontUtil {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static float drawString(boolean customFont, String text, int x, int y, Color color) {
        if (customFont) return NineWare.FONT_RENDERER.drawString(text, x, y, color);
        else return mc.fontRenderer.drawString(text, x, y, color.getRGB());
    }

    public static float drawStringWithShadow(boolean customFont, String text, int x, int y, Color color) {
        if (customFont) return NineWare.FONT_RENDERER.drawStringWithShadow(text, x, y, color);
        else return mc.fontRenderer.drawStringWithShadow(text, x, y, color.getRGB());
    }

    public static int getStringWidth(boolean customFont, String string) {
        if (customFont) {
            return NineWare.FONT_RENDERER.getStringWidth(string);
        } else {
            return mc.fontRenderer.getStringWidth(string);
        }
    }

    public static int getFontHeight(boolean customFont) {
        if (customFont) {
            return NineWare.FONT_RENDERER.getHeight();
        } else {
            return mc.fontRenderer.FONT_HEIGHT;
        }
    }

    private FontUtil() {
        throw new UnsupportedOperationException();
    }
}
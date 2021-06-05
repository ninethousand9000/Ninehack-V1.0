package me.ninethousand.ninehack.feature.gui.clickgui.theme;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.util.RenderUtil;
import me.ninethousand.ninehack.util.TextUtil;

import java.awt.*;

public abstract class Theme implements NineHack.Globals {
    public abstract void draw(int mouseX, int mouseY);

    protected abstract int[] drawCategory(Category category, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawFeature(Feature feature, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawBooleanSetting(Setting<Boolean> setting, int x, int y, int mouseX, int mouseY);

    protected abstract <E extends Enum<E>> int[] drawEnumSetting(Setting<Enum> setting, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawColorSetting(Setting<Color> setting, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawIntegerSetting(NumberSetting<Integer> setting, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawFloatSetting(NumberSetting<Float> setting, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawDoubleSetting(NumberSetting<Double> setting, int x, int y, int mouseX, int mouseY);

    protected abstract int[] drawBind(Feature feature , int key, int x, int y, int mouseX, int mouseY);

    protected abstract int getSettingsHeight(Feature feature);

    protected final boolean mouseHovering(final int left, final int top, final int right, final int bottom, final int mouseX, final int mouseY) {
        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }

    protected void drawRect(double left, double top, double right, double bottom, Color color) {
        RenderUtil.drawRect(left, top, right, bottom, color);
    }

    protected void drawOutline(double left, double top, double right, double bottom, Color color, float lineWidth) {
        RenderUtil.drawOutlineRect(left, top, right, bottom, color, lineWidth);
    }

    protected void drawText(String text, float x, float y, Color color) {
        NineHack.TEXT_MANAGER.drawStringWithShadow(text, x, y, color.getRGB());
    }
}

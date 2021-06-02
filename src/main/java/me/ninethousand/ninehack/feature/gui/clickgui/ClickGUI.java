package me.ninethousand.ninehack.feature.gui.clickgui;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.RenderUtil;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class ClickGUI implements NineHack.Globals {
    public static final int EDGE_SPACING_X = 2;
    public static final int EDGE_SPACING_Y = 20;
    public static final int FEATURE_SPACING = 1;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 14;
    public static final int FEATURE_HEIGHT = HEIGHT - 2;

    public static final Color ACCENT_COLOR = new Color(0x695AAD);
    public static final Color FEATURE_FILL_COLOR = new Color(0xFF474747, true);
    public static final Color FEATURE_BACKGROUND_COLOR = new Color(0xB74B4B4B, true);
    public static final Color FONT_COLOR = new Color(0xFFFFFF);

    public static Boolean customFont = false;

    public static boolean leftClicked = false, leftDown = false, rightClicked = false, rightDown = false;

    public static void drawGUI(int posX, int posY, int mouseX, int mouseY) {
        int x = posX + EDGE_SPACING_X + 20;
        int y;
        customFont = GUI.font.getValue();

        int totalY = 0;

        for (Category category : Category.values()) {
            y = posY + EDGE_SPACING_Y;
            totalY = y;

            // draw Header
            totalY += drawCategoryHeader(category, x, y, mouseX, mouseY);

            // if open draw features
            if (category.isOpenInGui()) {
                totalY += drawCategoryFeatures(category, x, totalY, mouseX, mouseY);
            }

            // if outline draw the outline
            if (GUI.outline.getValue()) {
                RenderUtil.drawOutlineRect(x + 0.3D, y, x + WIDTH , y + totalY - EDGE_SPACING_Y, ACCENT_COLOR, 1.2f);
            }

            // increase x to the next category x coord
            x += EDGE_SPACING_X + WIDTH;
        }
    }

    private static int drawCategoryHeader(Category category, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked)
            category.setOpenInGui(!category.isOpenInGui());

        RenderUtil.drawRect(x, y, x + WIDTH, y + HEIGHT, ACCENT_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(category.name(), x + (WIDTH / 2 - mc.fontRenderer.getStringWidth(category.name()) / 2), y + (HEIGHT / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 1, FONT_COLOR.getRGB());

        return HEIGHT;
    }

    private static int drawCategoryFeatures(Category category, int x, int y, int mouseX, int mouseY) {
        ArrayList<Feature> features = new ArrayList<>();
        int settingY = 0;

        for (Feature feature : FeatureManager.getFeaturesInCategory(category)) {
            features.add(feature);

            if (feature.isOpened())
                settingY += getSettingsHeight(feature);
        }

        int endY = (features.size() * FEATURE_HEIGHT) + features.size();

        RenderUtil.drawRect(x, y, x + WIDTH, y + endY +  settingY, FEATURE_BACKGROUND_COLOR);

        for (Feature feature : features) {
            if (mouseHovering(x + 2, y, x + WIDTH - 2, y + HEIGHT - 2, mouseX, mouseY)) {
                if (leftClicked) feature.setEnabled(!feature.isEnabled());
                if (rightClicked) feature.setOpened(!feature.isOpened());
            }

            RenderUtil.drawRect(x + 2, y + FEATURE_SPACING, x + WIDTH - 2, y + FEATURE_HEIGHT, feature.isEnabled() ? ACCENT_COLOR : FEATURE_FILL_COLOR);
            NineHack.TEXT_MANAGER.drawStringWithShadow(feature.getName(), x + 3, y + ((HEIGHT - 2) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());

            y += HEIGHT - 1;

            if (feature.isOpened()) {
                int boostY = drawFeatureSettings(feature, x, y, mouseX, mouseY);
                y += boostY;
                endY += boostY;
            }
        }

        return endY;
    }

    private static int drawFeatureSettings(Feature feature, int x, int y, int mouseX, int mouseY) {
        int boostY = 0;
        int ogY = y;
        int settingHeight;
        for (Setting<?> setting : feature.getSettings()) {
            settingHeight = 0;
            if (setting.getValue() instanceof Boolean) {
                Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                settingHeight = drawBooleanSetting(booleanSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Enum) {
                Setting<Enum> enumSetting = (Setting<Enum>) setting;
                settingHeight = drawEnumSetting(enumSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Color) {
                Setting<Color> colorSetting = (Setting<Color>) setting;
                settingHeight = drawColorSetting(colorSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Integer) {
                NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                settingHeight = drawIntegerSetting(integerSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Double) {
                NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                settingHeight = drawDoubleSetting(doubleSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Float) {
                NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                settingHeight = drawFloatSetting(floatSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            y += 1;
            boostY += 1;
        }
        /*RenderUtil.drawRect(x + 2, ogY - 1, x + 4, ogY + boostY - 1, ACCENT_COLOR);*/

        return boostY;
    }

    private static int drawBooleanSetting(Setting<Boolean> setting, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            if (leftClicked) setting.setValue(!setting.getValue());
            if (rightClicked) setting.setOpened(!setting.isOpened());
        }

        final char[] delimiters = { ' ', '_' };

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
        return FEATURE_HEIGHT;
    }

    private static <E extends Enum<E>> int drawEnumSetting(Setting<Enum> setting , int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            final E[] values = (E[]) setting.getValue().getDeclaringClass().getEnumConstants();
            final int ordinal = setting.getValue().ordinal();

            if (leftClicked) setting.setValue(values[ordinal + 1 >= values.length ? 0 : ordinal + 1]);
            else if (rightClicked) setting.setValue(values[ordinal - 1 < 0 ? values.length - 1 : ordinal - 1]);
        }

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawColorSetting(Setting<Color> setting , int x, int y, int mouseX, int mouseY) {


        return FEATURE_HEIGHT * 4;
    }

    private static int drawIntegerSetting(NumberSetting<Integer> setting , int x, int y, int mouseX, int mouseY) {
        int settingWidth = WIDTH - 2;

        int min = setting.getMin();
        int max = setting.getMax();

        if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x)) * 100.0 / ((x + settingWidth) - x);
            double newVal = percentError * ((max - min) / 100F) + min;
            setting.setValue((new BigDecimal(newVal).setScale(1, newVal > (max - min) / 2 ? RoundingMode.UP : RoundingMode.DOWN).intValue()));
        }

        int progress = settingWidth * (setting.getValue() - min) / (max - min);

        if (progress == 0)
            setting.setValue(min);
        if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(max);
            progress = settingWidth;
        }

        RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        RenderUtil.drawRect(x + 2, y - 1, progress == 0 ? x + 2 : x + progress, y + FEATURE_HEIGHT, ACCENT_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    /*private static int drawIntegerSetting(NumberSetting<Integer> setting , int x, int y, int mouseX, int mouseY) {
        int settingWidth = WIDTH - 5;
        int height = FEATURE_HEIGHT;

        x += 2;

        if (leftDown && mouseHovering(x, y, x + settingWidth, y + height, mouseX, mouseY)) {
            double percentError = (mouseX - (x)) * 100.0 / ((x + settingWidth) - x);
            double newVal = percentError * ((setting.getMax() - setting.getMin()) / 100F) + setting.getMin();
            setting.setValue(new BigDecimal(newVal).setScale(1, RoundingMode.UP).intValue());
        }

        int progress = settingWidth * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        RenderUtil.drawRect(x, y, x + progress, y + height, ACCENT_COLOR);

        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }*/

    private static int drawDoubleSetting(NumberSetting<Double> setting , int x, int y, int mouseX, int mouseY) {
        if (leftDown && mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x + 2)) * 100.0 / ((x + 2 + WIDTH - 2) - x + 2);
            double newVal = percentError * ((setting.getMax() - setting.getMin()) / 100F) + setting.getMin();
            setting.setValue(new BigDecimal(newVal).setScale(2, RoundingMode.UP).doubleValue());
        }
        int progress = (int) ((WIDTH - 2) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        RenderUtil.drawRect(x + 2, y - 1, x + 2 + progress, y + FEATURE_HEIGHT, ACCENT_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawFloatSetting(NumberSetting<Float> setting , int x, int y, int mouseX, int mouseY) {
        if (leftDown && mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x + 2)) * 100.0 / ((x + 2 + WIDTH - 2) - x + 2);
            double newVal = percentError * ((setting.getMax() - setting.getMin()) / 100F) + setting.getMin();
            setting.setValue(new BigDecimal(newVal).setScale(2, RoundingMode.UP).floatValue());
        }
        int progress = (int) ((WIDTH - 2) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin()));

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        RenderUtil.drawRect(x + 2, y - 1, x + 2 + progress, y + FEATURE_HEIGHT, ACCENT_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawBindSetting(int key , int x, int y, int mouseX, int mouseY) {
        return HEIGHT - 2;
    }

    private static final boolean mouseHovering(final int left, final int top, final int right, final int bottom, final int mouseX, final int mouseY) {
        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }

    private static int getSettingsHeight(Feature feature) {
        int boostY = 0;
        for (Setting<?> setting : feature.getSettings()) {
            if (setting.getValue() instanceof Color) {
                boostY += (FEATURE_HEIGHT + 1) * 4;
            }
            else {
                boostY += FEATURE_HEIGHT;
            }

            boostY += 1;
        }
        return boostY;
    }
}

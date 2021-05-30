package me.ninethousand.ninehack.feature.gui.clickgui;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.RenderUtil;
import me.ninethousand.ninehack.util.font.FontUtil;
import org.apache.commons.lang3.text.WordUtils;

import java.awt.*;
import java.util.ArrayList;

public class ClickGUI implements NineHack.Globals {
    public static final int EDGE_SPACING_X = 1;
    public static final int EDGE_SPACING_Y = 20;
    public static final int FEATURE_SPACING = 1;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 14;
    public static final int FEATURE_HEIGHT = HEIGHT - 2;

    public static final Color ACCENT_COLOR = new Color(0x695AAD);
    public static final Color FEATURE_FILL_COLOR = new Color(0x863D3D3D, true);
    public static final Color FEATURE_BACKGROUND_COLOR = new Color(0x57474747, true);
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
        FontUtil.drawStringWithShadow(customFont, category.name(), x + (WIDTH / 2 - mc.fontRenderer.getStringWidth(category.name()) / 2), y + (HEIGHT / 2) - (FontUtil.getFontHeight(customFont) / 2) - 1, FONT_COLOR);

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
            FontUtil.drawStringWithShadow(customFont, feature.getName(), x + 3, y + ((HEIGHT - 2) / 2) - (FontUtil.getFontHeight(customFont) / 2), FONT_COLOR);

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
                Setting<Integer> integerSetting = (Setting<Integer>) setting;
                settingHeight = drawIntegerSetting(integerSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Double) {
                Setting<Double> doubleSetting = (Setting<Double>) setting;
                settingHeight = drawDoubleSetting(doubleSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            if (setting.getValue() instanceof Float) {
                Setting<Float> floatSetting = (Setting<Float>) setting;
                settingHeight = drawFloatSetting(floatSetting, x, y, mouseX, mouseY);
                y += settingHeight;
                boostY += settingHeight;
            }
            y += 1;
            boostY += 1;
        }
        RenderUtil.drawRect(x + 2, ogY - 1, x + 4, ogY + boostY, ACCENT_COLOR);

        return boostY;
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

    private static int drawBooleanSetting(Setting<Boolean> setting, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x + 2, y, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            if (leftClicked) setting.setValue(!setting.getValue());
            if (rightClicked) setting.setOpened(!setting.isOpened());
        }

        final char[] delimiters = { ' ', '_' };

        RenderUtil.drawRect(x + 2, y, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        FontUtil.drawStringWithShadow(customFont, setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (FontUtil.getFontHeight(customFont) / 2), FONT_COLOR);
        FontUtil.drawStringWithShadow(customFont, WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + FontUtil.getStringWidth(customFont, setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (FontUtil.getFontHeight(customFont) / 2), Color.gray);
        return FEATURE_HEIGHT;
    }

    private static int drawEnumSetting(Setting<Enum> setting , int x, int y, int mouseX, int mouseY) {
        return FEATURE_HEIGHT;
    }

    private static int drawColorSetting(Setting<Color> setting , int x, int y, int mouseX, int mouseY) {
        return (FEATURE_HEIGHT + 1) * 4;
    }

    private static int drawIntegerSetting(Setting<Integer> setting , int x, int y, int mouseX, int mouseY) {
        return FEATURE_HEIGHT;
    }

    private static int drawDoubleSetting(Setting<Double> setting , int x, int y, int mouseX, int mouseY) {
        return FEATURE_HEIGHT;
    }

    private static int drawFloatSetting(Setting<Float> setting , int x, int y, int mouseX, int mouseY) {
        return FEATURE_HEIGHT;
    }

    private static int drawBindSetting(int key , int x, int y, int mouseX, int mouseY) {
        return HEIGHT - 2;
    }

    private static final boolean mouseHovering(final int posX, final int posY, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX > posX && mouseX < width && mouseY > posY && mouseY < height;
    }
}

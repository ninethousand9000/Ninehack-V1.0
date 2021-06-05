package me.ninethousand.ninehack.feature.gui.clickgui.theme.themes;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.ClientColor;
import me.ninethousand.ninehack.feature.gui.clickgui.theme.Theme;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.RenderUtil;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class NineHackTheme extends Theme {
    private int edgeSpaceX = 20;
    private int edgeSpaceY = 20;

    private int categorySpacing = 1;
    private int categoryWidth = 110;
    private int categoryHeight = 14;

    private int featureSpacingX = 1;
    private int featureSpacingY = 1;
    private int featureWidth = categoryWidth - 2;
    private int featureHeight = categoryHeight - 2;

    private int settingSpacingX = 1;
    private int settingSpacingY = 1;
    private int settingWidth = featureWidth - 2;
    private int settingHeight = featureHeight;

    private Color accentColor = new Color(162, 58, 245,255);
    private Color fillColor = new Color(0xFF474747, true);
    private Color backgroundColor = new Color(0x52181818, true);
    private Color fontColor = new Color(0xFFFFFF);

    public boolean leftClicked = false, leftDown = false, rightClicked = false, rightDown = false;
    public int keyDown = Keyboard.KEY_NONE;

    @Override
    public void draw(int mouseX, int mouseY) {
        int boostX = edgeSpaceX;
        int boostY;

        for (Category category : Category.values()) {
            boostY = edgeSpaceY;

            int[] drawnCategoryResults = drawCategory(category, boostX, boostY, mouseX, mouseY);
            boostY += drawnCategoryResults[1];

            if (category.isOpenInGui()) {
                boostY += featureSpacingY;
                for (Feature feature : FeatureManager.getFeaturesInCategory(category)) {
                    int[] featureResult = drawFeature(feature, boostX + featureSpacingX, boostY, mouseX, mouseY);
                    boostY += featureResult[1];
                    NineHack.log(featureResult[1] + "Value");
                }
            }
            boostX += drawnCategoryResults[0];
        }

    }

    @Override
    protected int[] drawCategory(Category category, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + categoryWidth, y + categoryHeight, mouseX, mouseY) && rightClicked)
            category.setOpenInGui(!category.isOpenInGui());

        drawRect(x, y, x + categoryWidth, y + categoryHeight, accentColor);
        drawText(category.name(), x + (categoryWidth / 2 - mc.fontRenderer.getStringWidth(category.name()) / 2), y + (categoryHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 1, fontColor);

        return new int[] {categoryWidth, categoryHeight};
    }

    @Override
    protected int[] drawFeature(Feature feature, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + categoryWidth, y + categoryHeight, mouseX, mouseY) && rightClicked)
            feature.setOpened(!feature.isOpened());

        if (mouseHovering(x, y, x + categoryWidth, y + categoryHeight, mouseX, mouseY) && leftClicked)
            feature.setEnabled(!feature.isEnabled());

        // draw bg
        drawRect(x - featureSpacingX, y - featureSpacingY, x + categoryWidth, y + featureHeight, backgroundColor);

        // draw feature button
        drawRect(x, y, x + featureWidth, y + featureHeight, feature.isEnabled() ? accentColor : fillColor);
        drawText(feature.getName(), x + (categoryWidth / 2 - mc.fontRenderer.getStringWidth(feature.getName()) / 2), y + (categoryHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 1, fontColor);

        y += featureHeight + featureSpacingY;

        /*if (feature.isOpened()) {
            for (Setting<?> setting : feature.getSettings()) {
                int settingX = x - settingSpacingX;
                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    int[] booleanPos = drawBooleanSetting(booleanSetting, settingX, y, mouseX, mouseY);
                    y += booleanPos[1];
                }
                if (setting.getValue() instanceof Enum) {
                    Setting<Enum> enumSetting = (Setting<Enum>) setting;
                    int[] enumPos = drawEnumSetting(enumSetting, settingX, y, mouseX, mouseY);
                    y += enumPos[1];
                }
                if (setting.getValue() instanceof Color) {
                    Setting<Color> colorSetting = (Setting<Color>) setting;
                    int[] colorPos = drawColorSetting(colorSetting, settingX, y, mouseX, mouseY);
                    y += colorPos[1];
                }
                if (setting.getValue() instanceof Integer) {
                    NumberSetting<Integer> integerSetting = (NumberSetting<Integer>) setting;
                    int[] integerPos = drawIntegerSetting(integerSetting, settingX, y, mouseX, mouseY);
                    y += integerPos[1];
                }
                if (setting.getValue() instanceof Float) {
                    NumberSetting<Float> floatSetting = (NumberSetting<Float>) setting;
                    int[] floatPos = drawFloatSetting(floatSetting, settingX, y, mouseX, mouseY);
                    y += floatPos[1];
                }
                if (setting.getValue() instanceof Double) {
                    NumberSetting<Double> doubleSetting = (NumberSetting<Double>) setting;
                    int[] doublePos = drawDoubleSetting(doubleSetting, settingX, y, mouseX, mouseY);
                    y += doublePos[1];
                }
                y += settingSpacingY;
            }
        }*/

        return new int[] {x, y};
    }

    @Override
    protected int[] drawBooleanSetting(Setting<Boolean> setting, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + settingWidth, y + settingHeight, mouseX, mouseY)) {
            if (leftClicked) setting.setValue(!setting.getValue());
            if (rightClicked) setting.setOpened(!setting.isOpened());
        }

        final char[] delimiters = { ' ', '_' };

        drawRect(x, y, x + settingWidth, y + settingHeight, fillColor);
        drawText(setting.getName() + ":", x + 4, y + (settingHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), fontColor);
        drawText(WordUtils.capitalizeFully(setting.getValue().toString()), x + 4 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + (settingHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray);
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected <E extends Enum<E>> int[] drawEnumSetting(Setting<Enum> setting, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y - 1, x + settingWidth, y + settingHeight, mouseX, mouseY)) {
            final E[] values = (E[]) setting.getValue().getDeclaringClass().getEnumConstants();
            final int ordinal = setting.getValue().ordinal();

            if (leftClicked) setting.setValue(values[ordinal + 1 >= values.length ? 0 : ordinal + 1]);
            else if (rightClicked) setting.setValue(values[ordinal - 1 < 0 ? values.length - 1 : ordinal - 1]);
        }

        drawRect(x, y, x + settingWidth, y + settingHeight, fillColor);
        drawText(setting.getName() + ":", x + 4, y + (settingHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), fontColor);
        drawText(WordUtils.capitalizeFully(setting.getValue().toString()), x + 4 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + (settingHeight / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray);

        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int[] drawColorSetting(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int[] drawIntegerSetting(NumberSetting<Integer> setting, int x, int y, int mouseX, int mouseY) {
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int[] drawFloatSetting(NumberSetting<Float> setting, int x, int y, int mouseX, int mouseY) {
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int[] drawDoubleSetting(NumberSetting<Double> setting, int x, int y, int mouseX, int mouseY) {
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int[] drawBind(Feature feature, int key, int x, int y, int mouseX, int mouseY) {
        return new int[]{x, y + settingHeight};
    }

    @Override
    protected int getSettingsHeight(Feature feature) {
        return 0;
    }
}

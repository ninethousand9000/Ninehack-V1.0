package me.ninethousand.ninehack.feature.gui.clickgui;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.ClientColor;
import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.ColorUtil;
import me.ninethousand.ninehack.util.RenderUtil;
import me.ninethousand.ninehack.util.Timer;
import net.minecraft.client.gui.GuiScreen;
import org.apache.commons.lang3.text.WordUtils;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Key;
import java.util.ArrayList;
import java.util.Locale;

public class ClickGUI extends GuiScreen {
    public static int EDGE_SPACING_X = 2;
    public static int EDGE_SPACING_Y = 20;
    public static int FEATURE_SPACING = 1;
    public static int WIDTH = 110;
    public static int HEIGHT = 16;
    public static int FEATURE_HEIGHT = HEIGHT - 2;
    public static int FEATURE_WIDTH = HEIGHT - 2;

    public static int cHeight = 0;

    public static final Timer timer = new Timer();

    public static Color HEADER_COLOR = new Color(61, 34, 194, 161);
    public static Color ACCENT_COLOR = new Color(80, 80, 174, 255);
    public static Color FEATURE_FILL_COLOR = new Color(0xFF474747, true);
    public static Color FEATURE_BACKGROUND_COLOR = new Color(0x52181818, true);
    public static Color FONT_COLOR = new Color(0xFFFFFF);

    public static boolean leftClicked = false, leftDown = false, rightClicked = false, rightDown = false;
    public static int keyDown = Keyboard.KEY_NONE;
    public static char typed = 't';

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawGUI(0, 0, mouseX, mouseY);

        leftClicked = false;
        rightClicked = false;
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0) {
            leftClicked = true;
            leftDown = true;
        }

        if (mouseButton == 1) {
            rightClicked = true;
            rightDown = true;
        }
    }

    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            leftClicked = false;
            leftDown = false;
        }

        if (state == 1) {
            rightClicked = false;
            rightDown = false;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        keyDown = keyCode;
        typed = typedChar;
    }

    protected static int getKey() {
        int temp = keyDown;
        keyDown = Keyboard.KEY_NONE;
        return temp;
    }

    protected static char getChar() {
        char temp = typed;
        typed = '\u0000';
        return temp;
    }

    @Override
    public void onGuiClosed() {
        GUI.guiOpen = false;
        GUI.INSTANCE.setEnabled(false);
    }

    public static void drawGUI(int posX, int posY, int mouseX, int mouseY) {
        int x = posX + EDGE_SPACING_X + 20;
        int y;

        int totalY = 0;

        for (Category category : Category.values()) {
            cHeight = 0;

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
                if (category.isOpenInGui() && !(FeatureManager.getFeaturesInCategory(category).size() <= 0)) totalY++;
                RenderUtil.drawOutlineRect(x + 0.3D, y, x + WIDTH , y + totalY - EDGE_SPACING_Y, HEADER_COLOR, 2f);
            }

            // increase x to the next category x coord
            x += EDGE_SPACING_X + WIDTH;
        }
    }

    private static int drawCategoryHeader(Category category, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked)
            category.setOpenInGui(!category.isOpenInGui());

        RenderUtil.drawRect(x, y, x + WIDTH, y + HEIGHT, HEADER_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(category.name(), x + (WIDTH / 2 - NineHack.TEXT_MANAGER.getStringWidth(category.name()) / 2), y + (HEIGHT / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 1, FONT_COLOR.getRGB());

        return HEIGHT;
    }

    private static int drawCategoryFeatures(Category category, int x, int y, int mouseX, int mouseY) {
        ArrayList<Feature> features = new ArrayList<>();
        int settingY = 1;

        for (Feature feature : FeatureManager.getFeaturesInCategory(category)) {
            features.add(feature);

            if (feature.isOpened()) {
                settingY += getSettingsHeight(feature);
                settingY += FEATURE_HEIGHT;
            }
        }

        int endY = (features.size() * FEATURE_HEIGHT);

        RenderUtil.drawRect(x, y, x + WIDTH, y + endY +  settingY, FEATURE_BACKGROUND_COLOR);

        for (Feature feature : features) {
            if (mouseHovering(x + 2, y, x + WIDTH - 2, y + HEIGHT - 2, mouseX, mouseY)) {
                if (leftClicked) feature.setEnabled(!feature.isEnabled());
                if (rightClicked) feature.setOpened(!feature.isOpened());
            }

            cHeight += ClientColor.step.getValue();
            Color featC = GUI.gradientFeatures.getValue() ? new Color(ClientColor.colorHeightMap.get(cHeight)) : ACCENT_COLOR;

            RenderUtil.drawRect(x + 2, y + FEATURE_SPACING, x + WIDTH - 2, y + FEATURE_HEIGHT, feature.isEnabled() ?  featC : FEATURE_FILL_COLOR);
            NineHack.TEXT_MANAGER.drawStringWithShadow(feature.getName(), x + 3, y + ((HEIGHT - 2) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());

            y += HEIGHT - 2;

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
        int settingHeight;
        for (Setting<?> setting : feature.getSettings()) {
            if (setting.isVisible()) {
                settingHeight = 0;
                if (setting.getValue() instanceof Boolean) {
                    Setting<Boolean> booleanSetting = (Setting<Boolean>) setting;
                    settingHeight = drawBooleanSetting(booleanSetting, x, y, mouseX, mouseY);
                    y += settingHeight;
                    boostY += settingHeight;
                }
                if (setting.getValue() instanceof String) {
                    Setting<String> stringSetting = (Setting<String>) setting;
                    settingHeight = drawStringSetting(stringSetting, x, y, mouseX, mouseY);
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
        }
        /*RenderUtil.drawRect(x + 2, ogY - 1, x + 4, ogY + boostY - 1, ACCENT_COLOR);*/
        boostY += drawBind(feature, keyDown, x, y, mouseX, mouseY);

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

    private static int drawStringSetting(Setting<String> setting , int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            if (leftClicked) setting.setTyping(!setting.isTyping());
            if (rightClicked) setting.setOpened(!setting.isOpened());
        }

        int key = getKey();
        char current = getChar();
        String currentS = String.valueOf(current);

        if (setting.isTyping()) {
            if (key == Keyboard.KEY_RETURN) {
                setting.setTyping(false);
            }
            else if (key == Keyboard.KEY_NONE) {
                // empty
            }
            else if ((key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK) && setting.getValue().length() > 0) {
                setting.setValue(setting.getValue().substring(0, setting.getValue().length() - 1));
            }
            else if (Character.isDigit(currentS.charAt(0)) || Character.isLetter(currentS.charAt(0)) || key == Keyboard.KEY_SPACE) {
//                setting.setValue(setting.getValue() + (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? Keyboard.getKeyName(key).toUpperCase() : Keyboard.getKeyName(key).toLowerCase()).charAt(0));
                setting.setValue(setting.getValue() + (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) ? String.valueOf(current).toUpperCase() : String.valueOf(current).toLowerCase()));
            }
        }

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getValue(), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

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
        if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked)
            setting.setOpened(!setting.isOpened());

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName(), x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        RenderUtil.drawRect(x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 2, y + (FEATURE_HEIGHT / 4) - 1, x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 9, y + 3 * (FEATURE_HEIGHT / 4), setting.getValue());
        RenderUtil.drawOutlineRect(x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 2, y + (FEATURE_HEIGHT / 4) - 1, x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName()) + 9, y + 3 * (FEATURE_HEIGHT / 4), FONT_COLOR, 1);


        if (setting.isOpened()) {
            Color newColor;
            NineHack.TEXT_MANAGER.drawStringWithShadow("..", x + WIDTH - 10, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 2, FONT_COLOR.getRGB());
            if (ClientColor.colorMode.getValue()) {
                float[] hsb = new float[3];
                hsb = Color.RGBtoHSB(setting.getValue().getRed(), setting.getValue().getGreen(), setting.getValue().getBlue(), hsb);

                float hueN = hsb[0];


                y += FEATURE_HEIGHT;
                int hue = drawColorSlider("Hue", (int) (hsb[0] * 255f), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int saturation = drawColorSlider("Saturation", (int) (hsb[1] * 255f), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int brightness = drawColorSlider("Brightness", (int) (hsb[2] * 255f), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int alpha = drawColorSlider("Alpha", setting.getValue().getAlpha(), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                drawRainbowButton(setting, x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;

                Color col = new Color(Color.HSBtoRGB(hue / 255f, saturation / 255f, brightness / 255f));
                int r = col.getRed();
                int g = col.getGreen();
                int b = col.getBlue();



                newColor = new Color(r, g, b, alpha);
            }
            else {
                y += FEATURE_HEIGHT;
                int red = drawColorSlider("Red", setting.getValue().getRed(), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int green = drawColorSlider("Green", setting.getValue().getGreen(), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int blue = drawColorSlider("Blue", setting.getValue().getBlue(), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                int alpha = drawColorSlider("Alpha", setting.getValue().getAlpha(), x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                drawRainbowButton(setting, x, y, mouseX, mouseY);
                y += FEATURE_HEIGHT;
                newColor = new Color(red, green, blue, alpha);
            }

            RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
            RenderUtil.drawRect(x + WIDTH - 10, y + FEATURE_HEIGHT / 4, x + WIDTH - 4, y + 3 * (FEATURE_HEIGHT / 4), ClientColor.GLOBAL_COLOR.getValue());
            RenderUtil.drawOutlineRect(x + WIDTH - 10, y + FEATURE_HEIGHT / 4, x + WIDTH - 4, y + 3 * (FEATURE_HEIGHT / 4), FONT_COLOR, 1);
            NineHack.TEXT_MANAGER.drawStringWithShadow("Color Sync", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());

            if (leftDown && mouseHovering(x, y, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
                setting.setValue(ClientColor.GLOBAL_COLOR.getValue());
            }
            else {
                setting.setValue(newColor);
            }
            return FEATURE_HEIGHT * 7;
        }
        else {
            NineHack.TEXT_MANAGER.drawStringWithShadow("...", x + WIDTH - NineHack.TEXT_MANAGER.getStringWidth("...") - 4, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2) - 2, FONT_COLOR.getRGB());
            return FEATURE_HEIGHT;
        }
    }

    private static int drawColorSlider(String colorType, int colorValue, int x, int y, int mouseX, int mouseY) {
        int settingWidth = WIDTH - 2;

        int min = 0;
        int max = 255;

        if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x)) * 100.0 / ((x + settingWidth) - x);
            double newVal = percentError * ((max - min) / 100F) + min;
            colorValue = (new BigDecimal(newVal).setScale(1, newVal > (max - min) / 2 ? RoundingMode.UP : RoundingMode.DOWN).intValue());
        }

        int progress = new BigDecimal((WIDTH - 2) * (colorValue - min) / (max - min)).setScale(2, RoundingMode.DOWN).intValue();

        if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            colorValue = min;
            progress = 0;
        }
        if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            colorValue = max;
            progress = settingWidth;
        }

        RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);

        cHeight += ClientColor.step.getValue();
        Color featC = GUI.gradientFeatures.getValue() ? new Color(ClientColor.colorHeightMap.get(cHeight)) : ACCENT_COLOR;

        if (progress > 0) {
            RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
        }

        NineHack.TEXT_MANAGER.drawStringWithShadow(colorType + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(String.valueOf(colorValue)), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(colorType + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return colorValue;
    }

    private static void drawRainbowButton(Setting<Color> setting, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            if (leftClicked) setting.setRainbow(!setting.isRainbow());
        }

        final char[] delimiters = { ' ', '_' };

        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);
        NineHack.TEXT_MANAGER.drawStringWithShadow("Rainbow" + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(String.valueOf(setting.isRainbow())), x + 6 + NineHack.TEXT_MANAGER.getStringWidth("Rainbow" + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());
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

        int progress = new BigDecimal((WIDTH - 2) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin())).setScale(2, RoundingMode.DOWN).intValue();

        if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(min);
            progress = 0;
        }
        if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(max);
            progress = settingWidth;
        }

        RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);

        cHeight += ClientColor.step.getValue();
        Color featC = GUI.gradientFeatures.getValue() ? new Color(ClientColor.colorHeightMap.get(cHeight)) : ACCENT_COLOR;

        if (progress > 0) {
            RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
        }

        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawDoubleSetting(NumberSetting<Double> setting , int x, int y, int mouseX, int mouseY) {
        int settingWidth = WIDTH - 2;

        double min = setting.getMin();
        double max = setting.getMax();

        if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x)) * 100.0 / ((x + settingWidth) - x);
            double newVal = percentError * ((max - min) / 100F) + min;
            setting.setValue((new BigDecimal(newVal).setScale(1, newVal > (max - min) / 2 ? RoundingMode.UP : RoundingMode.DOWN).doubleValue()));
        }

        int progress = new BigDecimal((WIDTH - 2) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin())).setScale(2, RoundingMode.DOWN).intValue();

        if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(min);
            progress = 0;
        }
        if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(max);
            progress = settingWidth;
        }

        RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);

        cHeight += ClientColor.step.getValue();
        Color featC = GUI.gradientFeatures.getValue() ? new Color(ClientColor.colorHeightMap.get(cHeight)) : ACCENT_COLOR;

        if (progress > 0) {
            RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
        }

        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawFloatSetting(NumberSetting<Float> setting , int x, int y, int mouseX, int mouseY) {
        int settingWidth = WIDTH - 2;

        float min = setting.getMin();
        float max = setting.getMax();

        if (leftDown && mouseHovering(x, y, x + settingWidth, y + FEATURE_HEIGHT, mouseX, mouseY)) {
            double percentError = (mouseX - (x)) * 100.0 / ((x + settingWidth) - x);
            double newVal = percentError * ((max - min) / 100F) + min;
            setting.setValue((new BigDecimal(newVal).setScale(1, newVal > (max - min) / 2 ? RoundingMode.UP : RoundingMode.DOWN).floatValue()));
        }

        int progress = new BigDecimal((WIDTH - 2) * (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin())).setScale(2, RoundingMode.DOWN).intValue();

        if (mouseHovering(x, y, x + 3, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(min);
            progress = 0;
        }
        if (mouseHovering(x + settingWidth - 1, y, x + settingWidth + 1, y + FEATURE_HEIGHT, mouseX, mouseY) && leftDown) {
            setting.setValue(max);
            progress = settingWidth;
        }

        RenderUtil.drawRect(x + 2, y - 1, x + settingWidth, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);

        cHeight += ClientColor.step.getValue();
        Color featC = GUI.gradientFeatures.getValue() ? new Color(ClientColor.colorHeightMap.get(cHeight)) : ACCENT_COLOR;

        if (progress > 0) {
            RenderUtil.drawRect(x + 2, y - 1, x + progress, y + FEATURE_HEIGHT, featC);
        }

        NineHack.TEXT_MANAGER.drawStringWithShadow(setting.getName() + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(setting.getValue().toString()), x + 6 + NineHack.TEXT_MANAGER.getStringWidth(setting.getName() + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());

        return FEATURE_HEIGHT;
    }

    private static int drawBind(Feature feature , int key, int x, int y, int mouseX, int mouseY) {
        if (leftClicked && mouseHovering(x + 2, y - 1, x + WIDTH -2, y + FEATURE_HEIGHT, mouseX, mouseY))
            feature.setBinding(!feature.isBinding());

        if (feature.isBinding() && (key == Keyboard.KEY_DELETE || key == Keyboard.KEY_BACK)) {
            feature.setKey(Keyboard.KEY_NONE);
            feature.setBinding(false);
        }

        if (feature.isBinding() && key == Keyboard.KEY_ESCAPE) {
            feature.setBinding(false);
        }

        if (feature.isBinding() && !(key == Keyboard.KEY_NONE || key == Keyboard.KEY_DELETE)) {
            feature.setKey(key);
            feature.setBinding(false);
        }
        RenderUtil.drawRect(x + 2, y - 1, x + WIDTH - 2, y + FEATURE_HEIGHT, FEATURE_FILL_COLOR);

        final String keyName = feature.isBinding() == true ? "..." : Keyboard.getKeyName(feature.getKey());
        NineHack.TEXT_MANAGER.drawStringWithShadow("Bind" + ":", x + 6, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), FONT_COLOR.getRGB());
        NineHack.TEXT_MANAGER.drawStringWithShadow(WordUtils.capitalizeFully(keyName), x + 6 + NineHack.TEXT_MANAGER.getStringWidth("Bind" + ":") + 2, y + ((FEATURE_HEIGHT) / 2) - (NineHack.TEXT_MANAGER.getFontHeight() / 2), Color.gray.getRGB());



        return FEATURE_HEIGHT;
    }


    private static final boolean mouseHovering(final int left, final int top, final int right, final int bottom, final int mouseX, final int mouseY) {
        return mouseX > left && mouseX < right && mouseY > top && mouseY < bottom;
    }

    private static int getSettingsHeight(Feature feature) {
        int boostY = 0;
        for (Setting<?> setting : feature.getSettings()) {
            if (setting.isVisible()) {
                if (setting.getValue() instanceof Color) {
                    if (setting.isOpened()) {
                        boostY += FEATURE_HEIGHT * 7;
                    }
                    else {
                        boostY += FEATURE_HEIGHT;
                    }
                }
                else {
                    boostY += FEATURE_HEIGHT;
                }

                boostY += 1;
            }
        }
        return boostY;
    }
}

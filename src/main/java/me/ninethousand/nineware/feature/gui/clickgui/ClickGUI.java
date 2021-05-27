package me.ninethousand.nineware.feature.gui.clickgui;

import me.ninethousand.nineware.NineWare;
import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.setting.Setting;
import me.ninethousand.nineware.managers.FeatureManager;
import me.ninethousand.nineware.util.RenderUtil;
import me.ninethousand.nineware.util.font.FontUtil;

import java.awt.*;
import java.util.ArrayList;

public class ClickGUI implements NineWare.Globals {
    public static final int X_SPACING = 1;
    public static final int Y_SPACING = 20;
    public static final int WIDTH = 100;
    public static final int HEIGHT = 14;

    public static final Color ACCENT_COLOR = new Color(0x695AAD);
    public static final Color FEATURE_FILL_COLOR = new Color(0x863D3D3D, true);
    public static final Color FEATURE_BACKGROUND_COLOR = new Color(0x3B3D3D3D, true);
    public static final Color FONT_COLOR = new Color(0xFFFFFF);

    public static final Boolean CUSTOM_FONT = false;

    public static boolean leftClicked = false, leftDown = false, rightClicked = false, rightDown = false;

    public static void drawGUI(int posX, int posY, int mouseX, int mouseY) {
        int x = posX + X_SPACING + 20;
        int y = Y_SPACING;

        for (Category category : Category.values()) {
            y = posY + Y_SPACING;
            drawCategory(category, x, y, mouseX, mouseY);

            if (category.isOpenInGui()) {
                drawCategoryFeatures(category, x, y + HEIGHT, mouseX, mouseY);
            }

            x += X_SPACING + WIDTH;
        }
    }

    private static void drawCategory(Category category, int x, int y, int mouseX, int mouseY) {
        if (mouseHovering(x, y, x + WIDTH, y + HEIGHT, mouseX, mouseY) && rightClicked)
            category.setOpenInGui(!category.isOpenInGui());

        RenderUtil.drawRect(x, y, x + WIDTH, y + HEIGHT, ACCENT_COLOR);
        FontUtil.drawStringWithShadow(CUSTOM_FONT, category.name(), x + (WIDTH / 2 - mc.fontRenderer.getStringWidth(category.name()) / 2), y + (HEIGHT / 2) - (FontUtil.getFontHeight(CUSTOM_FONT) / 2) - 1, FONT_COLOR);
    }

    private static void drawCategoryFeatures(Category category, int x, int y, int mouseX, int mouseY) {
        ArrayList<Feature> features = new ArrayList<>();

        for (Feature feature : FeatureManager.getFeaturesInCategory(category)) {
            features.add(feature);
        }

        int endY = features.size() * HEIGHT;

        RenderUtil.drawRect(x, y, x + WIDTH, y + endY, FEATURE_BACKGROUND_COLOR);
        RenderUtil.drawOutlineRect(x + 0.3D, y, x + WIDTH , y + endY, ACCENT_COLOR, 1.2f);

        y += 2;
        for (Feature feature : features) {
            RenderUtil.drawRect(x + 2, y, x + WIDTH - 2, y + HEIGHT - 2, FEATURE_FILL_COLOR);
            FontUtil.drawStringWithShadow(CUSTOM_FONT, feature.getName(), x + 3, y + ((HEIGHT - 2) / 2) - (FontUtil.getFontHeight(CUSTOM_FONT) / 2), FONT_COLOR);

            y += HEIGHT - 1;
        }
    }

    private static void drawFeatureSettings(Feature feature, int x, int y, int mouseX, int mouseY) {
        ArrayList<Setting<?>> settings = new ArrayList<>();
        settings = feature.getSettings();


    }

    private static void drawBooleanSetting(Setting<Boolean> setting, int x, int y) {

    }

    private static void drawEnumSetting(Setting<Enum> setting , int x, int y) {

    }

    private static final boolean mouseHovering(final int posX, final int posY, final int width, final int height, final int mouseX, final int mouseY) {
        return mouseX > posX && mouseX < width && mouseY > posY && mouseY < height;
    }
}

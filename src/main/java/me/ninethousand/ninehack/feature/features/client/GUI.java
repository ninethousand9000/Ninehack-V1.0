package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUI;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;

@NineHackFeature(name = "Gui", description = "Sexy ClickGUI", category = Category.Client, key = NineHack.DEFAULT_GUI_KEY)
public class GUI extends Feature {
    public static Feature INSTANCE;
    public static boolean guiOpen = false;

    public static final NumberSetting<Integer> width = new NumberSetting<>("Width", 80, 110, 130, 1);
    public static final NumberSetting<Integer> height = new NumberSetting<>("Height", 12, 14, 20, 1);
    public static final Setting<Boolean> outline = new Setting<>("Outline", true);
    public static final Setting<Boolean> gradientFeatures = new Setting<>("Gradient", false);
    public static final Setting<Color> headerColor = new Setting<>("Header", ClickGUI.HEADER_COLOR);
    public static final Setting<Color> accentColor = new Setting<>("Accent", ClickGUI.ACCENT_COLOR);
    public static final Setting<Color> featureFill = new Setting<>("Fill", ClickGUI.FEATURE_FILL_COLOR);
    public static final Setting<Color> featureBackground = new Setting<>("Background", ClickGUI.FEATURE_BACKGROUND_COLOR);
    public static final Setting<Color> fontColor = new Setting<>("Font", ClickGUI.FONT_COLOR);

    public static final GuiScreen gui = new ClickGUI();

    public GUI() {
        addSettings(
                width,
                height,
                outline,
                gradientFeatures,
                headerColor,
                accentColor,
                featureFill,
                featureBackground,
                fontColor
        );
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(gui);
        guiOpen = true;
        ClickGUI.WIDTH = width.getValue();
        ClickGUI.HEIGHT = height.getValue();
        ClickGUI.FEATURE_HEIGHT = height.getValue() - 2;
    }

    @Override
    public void onUpdate() {
        ClickGUI.HEADER_COLOR = headerColor.getValue();
        ClickGUI.ACCENT_COLOR = accentColor.getValue();
        ClickGUI.FEATURE_FILL_COLOR = featureFill.getValue();
        ClickGUI.FEATURE_BACKGROUND_COLOR = featureBackground.getValue();
        ClickGUI.FONT_COLOR = fontColor.getValue();
    }
}

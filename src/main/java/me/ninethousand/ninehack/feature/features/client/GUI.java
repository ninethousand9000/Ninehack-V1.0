package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUI;
import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUIScreen;
import me.ninethousand.ninehack.feature.setting.Setting;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.awt.*;

@NineHackFeature(name = "Gui", description = "Sexy ClickGUI", category = Category.Client, key = NineHack.DEFAULT_GUI_KEY)
public class GUI extends Feature {
    public static Feature INSTANCE;
    public static boolean guiOpen = false;

    public static final Setting<Boolean> pause = new Setting<>("Pause Game", false);
    public static final Setting<Boolean> outline = new Setting<>("Outline", true);
    public static final Setting<Color> accentColor = new Setting<>("Accent", new Color(0x695AAD));
    public static final Setting<Color> featureFill = new Setting<>("Fill", new Color(0xFF474747, true));
    public static final Setting<Color> featureBackground = new Setting<>("Background", new Color(0x52181818, true));
    public static final Setting<Color> fontColor = new Setting<>("Font", new Color(0xFFFFFFFF, true));

    public static final GuiScreen gui = new ClickGUIScreen();

    public GUI() {
        addSettings(
                outline,
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
    }

    @Override
    public void onUpdate() {
        ClickGUI.ACCENT_COLOR = accentColor.getValue();
        ClickGUI.FEATURE_FILL_COLOR = featureFill.getValue();
        ClickGUI.FEATURE_BACKGROUND_COLOR = featureBackground.getValue();
        ClickGUI.FONT_COLOR = fontColor.getValue();
    }
}

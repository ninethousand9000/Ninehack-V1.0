package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUIScreen;
import me.ninethousand.ninehack.feature.setting.Setting;
import org.lwjgl.input.Keyboard;

@NineHackFeature(name = "Gui", description = "Sexy ClickGUI", category = Category.Client, key = Keyboard.KEY_G)
public class GUI extends Feature {
    public static Feature INSTANCE;
    public static boolean guiOpen = false;
    public static final Setting<Boolean> outline = new Setting<>("Outline", true);
    public static final Setting<Boolean> font = new Setting<>("Custom Font", true);

    public GUI() {
        addSettings(outline, font);
    }

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGUIScreen());
        guiOpen = true;
        disable();
    }
}

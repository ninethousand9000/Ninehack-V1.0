package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.gui.clickgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

@NineHackFeature(name = "Gui", description = "Sexy ClickGUI", category = Category.Client, key = Keyboard.KEY_G)
public class GUI extends Feature {
    public static final Feature INSTANCE = new GUI();
    public static boolean guiOpen = false;

    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGUIScreen());
        guiOpen = true;
        disable();
    }
}

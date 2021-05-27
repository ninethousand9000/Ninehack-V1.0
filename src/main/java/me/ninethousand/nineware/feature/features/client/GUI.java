package me.ninethousand.nineware.feature.features.client;

import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.NineWareFeature;
import me.ninethousand.nineware.feature.gui.clickgui.ClickGUIScreen;
import org.lwjgl.input.Keyboard;

@NineWareFeature(name = "ClickGUI", description = "Sexy ClickGUI", category = Category.Client, key = Keyboard.KEY_G)
public class GUI extends Feature {
    @Override
    public void onEnable() {
        mc.displayGuiScreen(new ClickGUIScreen());
    }
}

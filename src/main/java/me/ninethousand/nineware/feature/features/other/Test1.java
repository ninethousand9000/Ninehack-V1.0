package me.ninethousand.nineware.feature.features.other;

import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.NineWareFeature;
import me.ninethousand.nineware.feature.setting.Setting;
import org.lwjgl.input.Keyboard;

@NineWareFeature(name = "Test1", description = "Sends A Chat Message", category = Category.Other, key = Keyboard.KEY_I)
public class Test1 extends Feature {
    public static final Setting<Boolean> spam = new Setting<>("Spam", "Spams", true);

    public Test1() {
        addSettings(spam);
    }

    @Override
    public void onEnable() {
        mc.player.sendChatMessage("Hi");
    }
}

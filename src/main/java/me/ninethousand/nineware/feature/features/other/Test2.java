package me.ninethousand.nineware.feature.features.other;

import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.NineWareFeature;
import me.ninethousand.nineware.feature.setting.Setting;

@NineWareFeature(name = "Test2", description = "Sends A Chat Message", category = Category.Other)
public class Test2 extends Feature {
    public static final Setting<Boolean> spam = new Setting<>("Spam", "Spams", true);

    public Test2() {
        addSettings(spam);
    }

    @Override
    public void onUpdate() {
        mc.player.sendChatMessage("HEHEW)APFDIWdf");
    }
}
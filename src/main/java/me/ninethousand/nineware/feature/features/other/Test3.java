package me.ninethousand.nineware.feature.features.other;

import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.NineWareFeature;
import me.ninethousand.nineware.feature.setting.Setting;

@NineWareFeature(name = "Test3", description = "Sends A Chat Message", category = Category.Other)
public class Test3 extends Feature {
    public static final Setting<Boolean> spam = new Setting<>("Spam", "Spams", true);

    public Test3() {
        addSettings(spam);
    }
}

package me.ninethousand.ninehack.feature.features.combat;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;

@NineHackFeature(name = "Offhand", description = "Puts item in offhand", category = Category.Combat)
public class Offhand extends Feature {
    public static final Setting<Boolean> searchHotbar = new Setting<>("Search Hotbar", true);

}

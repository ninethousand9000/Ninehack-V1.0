package me.ninethousand.ninehack.feature.features.other;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;

@NineHackFeature(name = "Chat Mods", description = "Modify Chat", category = Category.Other)
public class ChatMods extends Feature{
    public static Feature INSTANCE;

    public static final Setting<Boolean> clear = new Setting<>("Clear Chatbox", true);

    public ChatMods() {
        addSettings(clear);
    }
}

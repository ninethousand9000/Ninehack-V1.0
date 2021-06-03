package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;

@NineHackFeature(name = "Font", description = "Modify the client fonts", category = Category.Client)
public class ClientFont extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Boolean> shadow = new Setting<>("Shadow", true);
    public static final NumberSetting<Integer> size = new NumberSetting<>("Size", 10, 17, 30, 0);
    public static final Setting<FontStyle> style = new Setting<>("Style", FontStyle.Normal);

    public ClientFont() {
        addSettings(
                shadow,
                size,
                style
                );
    }

    @Override
    public void onEnable() {
        NineHack.TEXT_MANAGER.update();
    }

    public enum FontStyle {
        Normal(0),
        Bold(1),
        Italic(2);
        
        private final int style;
        
        FontStyle(final int style) {
            this.style = style;
        }
    }
}

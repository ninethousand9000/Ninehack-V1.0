package me.ninethousand.ninehack.feature.hud.components.textcomponent.textcomponents;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.ClientFont;
import me.ninethousand.ninehack.feature.hud.components.Component;
import me.ninethousand.ninehack.feature.hud.components.textcomponent.TextComponent;

public class Watermark extends TextComponent {
    public static Component INSTANCE;

    public Watermark(String text, Priority priority, Corner corner) {
        super(text, priority, corner);
    }

    @Override
    public void drawComponent(int x, int y) {
        drawString("NineHack " + NineHack.MOD_VERSION, x, y, staticRainbow(), 10f, ClientFont.shadow.getValue());
    }
}

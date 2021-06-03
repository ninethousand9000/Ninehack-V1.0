package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.Render2DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.gui.hud.components.Component;
import me.ninethousand.ninehack.feature.gui.hud.components.textcomponent.TextComponent;
import me.ninethousand.ninehack.feature.gui.hud.components.textcomponent.textcomponents.Watermark;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.ComponentManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;

@NineHackFeature(name = "HUD", description = "Based HUD", category = Category.Client)
public class HUD extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Boolean> rainbow = new Setting<>("Rainbow", true);

    public HUD() {
        addSettings(rainbow);
    }

    @Override
    public void on2DRenderEvent(Render2DEvent event) {
        int x = 1;
        int y = 1;

        for (TextComponent textComponent : ComponentManager.getTextComponentsByCorner(Component.Corner.TopLeft)) {
            if (textComponent.getClass() == Watermark.class) {
                textComponent.drawComponent(x, y);
                y += NineHack.TEXT_MANAGER.scaledHeight;
            }
        }
    }
}

package me.ninethousand.ninehack.feature.features.client;

import com.olliem5.pace.annotation.PaceHandler;
import com.olliem5.pace.modifier.EventPriority;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import me.ninethousand.ninehack.feature.hud.Component;
import me.ninethousand.ninehack.feature.hud.components.textcomponent.TextComponent;
import me.ninethousand.ninehack.feature.hud.components.textcomponent.textcomponents.Watermark;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.ComponentManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@NineHackFeature(name = "HUD", description = "Based HUD", category = Category.Client)
public class HUD extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Boolean> rainbow = new Setting<>("Rainbow", true);

    public HUD() {
        addSettings(rainbow);
    }

    @PaceHandler(priority = EventPriority.LOWEST)
    public void onHudRenderEvent(RenderGameOverlayEvent.Text hudRenderEvent) {
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

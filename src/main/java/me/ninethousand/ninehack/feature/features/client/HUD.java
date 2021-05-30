package me.ninethousand.ninehack.feature.features.client;

import com.olliem5.pace.annotation.PaceHandler;
import com.olliem5.pace.modifier.EventPriority;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.HudRenderEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.NineHackFeature;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.awt.*;

@NineHackFeature(name = "HUD", description = "Based HUD", category = Category.Client)
public class HUD extends Feature {
    public static Feature INSTANCE;

    @PaceHandler(priority = EventPriority.LOWEST)
    public void onHudRenderEvent(RenderGameOverlayEvent.Text hudRenderEvent) {
        NineHack.FONT_RENDERER.drawStringWithShadow("NineHack " + NineHack.MOD_VERSION, 1, 1, Color.white);
    }
}

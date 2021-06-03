package me.ninethousand.ninehack.feature.features.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import com.olliem5.pace.annotation.PaceHandler;
import com.olliem5.pace.modifier.EventPriority;
import me.ninethousand.ninehack.event.events.TotemPopEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.NotificationManager;
import me.ninethousand.ninehack.util.ChatUtil;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.HashMap;
import java.util.Map;

@NineHackFeature(name = "Notify", description = "Notify you when things happen", category = Category.Client)
public class Notify extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Boolean> totemPop = new Setting<>("Totem Pop", false);

    public static final Setting<ModuleToggleMode> moduleToggle = new Setting<>("Module Toggle", ModuleToggleMode.Chat);

    public Notify() {
        addSettings(
                totemPop,
                moduleToggle
        );
    }
    private final Map<String, Integer> totemPopMap = new HashMap<>();

    @PaceHandler
    public void onTotemPop(TotemPopEvent event) {
        if (nullCheck()) return;

        int pops = totemPopMap.getOrDefault(event.getEntity().getName(), 0) + 1;

        totemPopMap.put(event.getEntity().getName(), pops);

        String message;

        message = ChatFormatting.AQUA + event.getEntity().getName() + ChatFormatting.RESET + " popped " + ChatFormatting.RED + pops + ChatFormatting.RESET + (totemPopMap.get(event.getEntity().getName()) == 1 ? " totem" : " totems");

        if (totemPop.getValue()) {
            ChatUtil.sendClientMessageSimple(message);
        }
    }

    @PaceHandler(priority = EventPriority.LOWEST)
    public void onHudRenderEvent(RenderGameOverlayEvent.Text hudRenderEvent) {
        NotificationManager.render();
    }

    public enum ModuleToggleMode {
        None,
        Chat,
        HUD
    }
}

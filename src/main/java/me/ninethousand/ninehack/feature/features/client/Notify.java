package me.ninethousand.ninehack.feature.features.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.Render2DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.NotificationManager;
import me.ninethousand.ninehack.util.ChatUtil;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

import java.util.HashMap;

@NineHackFeature(name = "Notify", description = "Notify you when things happen", category = Category.Client)
public class Notify extends Feature {
    public static Notify INSTANCE;

    public static final Setting<Boolean> totemPop = new Setting<>("Totem Pop", false);

    public static final Setting<ModuleToggleMode> moduleToggle = new Setting<>("Module Toggle", ModuleToggleMode.Chat);

    public Notify() {
        addSettings(
                totemPop,
                moduleToggle
        );
    }
    public static HashMap<String, Integer> popMap = new HashMap();

    public void onDeath(EntityPlayer player) {
        if (popMap.containsKey(player.getName())) {
            int l_Count = popMap.get(player.getName());
            popMap.remove(player.getName());
            if (l_Count == 1) {
                ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totem");
            } else {
                ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.getName() + " died after popping " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totems");
            }
        }
    }

    public void onTotemPop(EntityPlayer player) {
        if (nullCheck()) return;

        int l_Count = 1;
        if (popMap.containsKey(player.getName())) {
            l_Count = popMap.get(player.getName());
            popMap.put(player.getName(), ++l_Count);
        } else {
            popMap.put(player.getName(), l_Count);
        }
        if (l_Count == 1) {
            ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totem");
        } else {
            ChatUtil.sendClientMessageSimple(ChatFormatting.RED + player.getName() + " popped " + ChatFormatting.GREEN + l_Count + ChatFormatting.RED + " totems.");
        }
    }

    @Override
    public void on2DRenderEvent(Render2DEvent event) {
        NotificationManager.render();
    }

    public enum ModuleToggleMode {
        None,
        Chat,
        HUD
    }
}

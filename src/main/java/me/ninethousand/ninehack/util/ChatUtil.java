package me.ninethousand.ninehack.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

public class ChatUtil implements NineHack.Globals {
    private static final String prefix = ChatFormatting.BLUE + "[NineHack]";

    public static void sendClientMessageSimple(String message) {
        mc.player.sendMessage(new TextComponentString(prefix + " " + ChatFormatting.WHITE + message));
    }

    public static void sendClientMessage(String message) {
        if (mc.player != null) {
            final ITextComponent itc = new TextComponentString(prefix + " " + message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("ninehack owns all!"))));
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
        }
    }

    public static void sendChatMessage(String message) {
        mc.player.sendChatMessage(message);
    }
}

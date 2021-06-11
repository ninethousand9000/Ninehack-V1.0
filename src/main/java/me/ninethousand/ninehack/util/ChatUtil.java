package me.ninethousand.ninehack.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.Chat;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.HoverEvent;

public class ChatUtil implements NineHack.Globals {

    public static void sendClientMessageSimple(String message) {
        mc.player.sendMessage(new TextComponentString(getPrefix() + " " + ChatFormatting.WHITE + message));
    }

    public static void sendClientMessage(String message) {
        if (mc.player != null) {
            final ITextComponent itc = new TextComponentString(getPrefix() + " " + message).setStyle(new Style().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TextComponentString("ninehack owns all!"))));
            mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(itc, 5936);
        }
    }

    public static void sendChatMessage(String message) {
        mc.player.sendChatMessage(message);
    }

    public static String getPrefix() {
        String prefix = Chat.prefixString.getValue();

        switch (Chat.prefixBracket.getValue()) {
            case Arrow:
                return TextUtil.coloredString("<" + prefix + ">", Chat.prefixColor.getValue());
            case Double:
                return TextUtil.coloredString(prefix + " >>", Chat.prefixColor.getValue());
            case Square:
                return TextUtil.coloredString("[" + prefix + "]", Chat.prefixColor.getValue());
            case Round:
                return TextUtil.coloredString("(" + prefix + ")", Chat.prefixColor.getValue());
            case Curly:
                return TextUtil.coloredString("{" + prefix + "}", Chat.prefixColor.getValue());
        }
        return TextUtil.coloredString(prefix, Chat.prefixColor.getValue());
    }
}

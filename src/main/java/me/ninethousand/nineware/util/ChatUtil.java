package me.ninethousand.nineware.util;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.nineware.NineWare;
import net.minecraft.util.text.TextComponentString;

public class ChatUtil implements NineWare.Globals {
    private static final String prefix = ChatFormatting.DARK_RED + "[" + ChatFormatting.RED + "NineWare" + ChatFormatting.DARK_RED + "]";

    public static void sendClientMessage(String message) {
        mc.player.sendMessage(new TextComponentString(prefix + " " + ChatFormatting.WHITE + message));
    }

    public static void sendChatMessage(String message) {
        mc.player.sendChatMessage(message);
    }
}

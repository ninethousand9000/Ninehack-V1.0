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
        String prefix = "Hi";

        switch (Chat.prefixString.getValue()) {
            case NineHack:
                prefix = "NineHack";
                break;
            case Dev:
                prefix = "ninethousand.dev";
                break;
            case CurryWare:
                prefix = "CurryWare";
                break;
            case Rwahack:
                prefix = "Rwahack";
                break;
            case Reapsense:
                prefix = "Reapsense 2.0";
                break;
            case BoboHack:
                prefix = "BoboHack";
                break;
            case x8Hack:
                prefix = "x8Hack";
                break;
            case PedroHack:
                prefix = "PedroHack";
                break;
            case MomHack:
                prefix = "MomHack";
                break;
            case xcc7Ware:
                prefix = "xcc7ware";
                break;
            case WhaleHack:
                prefix = "WhaleHack";
                break;
            case Skylight:
                prefix = "Skylight";
                break;
            case Jimboware:
                prefix = "JimboWare";
                break;
            case JoeWare:
                prefix = "JoeWare";
                break;
        }

        return TextUtil.coloredString("[" + prefix + "]", Chat.prefixColor.getValue());
    }
}

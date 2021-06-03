package me.ninethousand.ninehack.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ninethousand.ninehack.NineHack;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

import java.util.Objects;

public class RPCUtil implements NineHack.Globals {
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static void startup() {
        NineHack.log("Discord RPC Started.");

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize(NineHack.APP_ID, handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "swag";

        discordRichPresence.largeImageText = "Version " + NineHack.MOD_VERSION;

        discordRPC.Discord_UpdatePresence(discordRichPresence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    details = NineHack.NAME_VERSION;
                    state = "Main Menu";

                    if (mc.isIntegratedServerRunning()) {
                        state = "Singleplayer | " + Objects.requireNonNull(mc.getIntegratedServer()).getWorldName();
                    } else if (mc.currentScreen instanceof GuiMultiplayer) {
                        state = "Multiplayer Menu";
                    } else if (mc.currentScreen instanceof GuiWorldSelection) {
                        state = "Singleplayer Menu";
                    } else if (mc.getCurrentServerData() != null) {
                        state = "Server | " + mc.getCurrentServerData().serverIP.toLowerCase();
                    }

                    discordRichPresence.details = details;
                    discordRichPresence.state = state;

                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                } catch (Exception exception) {
                    exception.printStackTrace();
                } try {
                    Thread.sleep(5000L);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void shutdown() {
        NineHack.log("Discord RPC is shutting down!");

        discordRPC.Discord_Shutdown();
    }
}

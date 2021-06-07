package me.ninethousand.ninehack.util;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.features.client.RPC;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiWorldSelection;

import java.util.Objects;

public class RPCUtil implements NineHack.Globals {
    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    private static String details;
    private static String state;

    public static String id = "847411619914711061";

    public static final void update() {
        if (RPC.rpcMode.getValue() == RPC.RPCMode.NineHack) {
            id = "847411619914711061";

            discordRichPresence.largeImageKey = "swag";
            discordRichPresence.largeImageText = "Version " + NineHack.MOD_VERSION;
        }
        else if (RPC.rpcMode.getValue() == RPC.RPCMode.TuxHack) {
            id = "851171769850134579";
        }
    }

    public static void startup() {
        NineHack.log("Discord RPC Started.");

        update();

        DiscordEventHandlers handlers = new DiscordEventHandlers();

        discordRPC.Discord_Initialize(id, handlers, true, "");

        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;

        discordRPC.Discord_UpdatePresence(discordRichPresence);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    if (RPC.rpcMode.getValue() == RPC.RPCMode.NineHack) {
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
                    }

                    else if (RPC.rpcMode.getValue() == RPC.RPCMode.TuxHack) {
                        details = state = "pvping noobs @ " + mc.getCurrentServerData().serverIP.toLowerCase();
                        state = "TuxIsCool is based";
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

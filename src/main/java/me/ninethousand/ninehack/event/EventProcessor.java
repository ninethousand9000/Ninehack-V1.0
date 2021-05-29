package me.ninethousand.ninehack.event;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraft.client.gui.GuiChat;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

public final class EventProcessor implements NineHack.Globals {
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                for (Feature feature : FeatureManager.getFeatures()) {
                    if (feature.getKey() == Keyboard.getEventKey()) {
                        feature.toggle();
                    }
                }

                String keyName = Keyboard.getKeyName(Keyboard.getEventKey());

                // if someone presses the command prefix, it will open the chat box
                // with the key prefix already typed in (not working atm :/)
                if (keyName.equalsIgnoreCase(NineHack.CHAT_PREFIX)) {
                    mc.displayGuiScreen(new GuiChat(NineHack.CHAT_PREFIX));
                }

                try {
                    NineHack.EVENT_BUS.dispatchEvent(event);
                } catch (Exception ignored) {}
            }
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        for (Feature module : FeatureManager.getFeatures()) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
        }
    }

    @SubscribeEvent
    public void onClientChat(ClientChatEvent event) {
        if (event.getMessage().startsWith(NineHack.CHAT_PREFIX)) {
            event.setCanceled(true);

            mc.ingameGUI.getChatGUI().addToSentMessages(event.getOriginalMessage());

            CommandManager.parseCommand(event.getMessage().replace(NineHack.CHAT_PREFIX, ""));
        } else {
            event.setMessage(event.getMessage());
        }
    }

    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onFOVModify(EntityViewRenderEvent.FOVModifier event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onUseItem(LivingEntityUseItemEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onInputUpdate(InputUpdateEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onFogColours(EntityViewRenderEvent.FogColors event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onPlayerPushOutOfBlocks(PlayerSPPushOutOfBlocksEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        NineHack.EVENT_BUS.dispatchEvent(event);
    }

//    @PaceHandler
//    public void onPacketReceive(PacketEvent.Receive event) {
//        if (event.getPacket() instanceof SPacketEntityStatus) {
//            SPacketEntityStatus packet = (SPacketEntityStatus) event.getPacket();
//
//            if (packet.getOpCode() == 35) {
//                Entity entity = packet.getEntity(mc.world);
//
//                if (entity instanceof EntityPlayer && entity != mc.player) {
//                    NinekWare.EVENT_BUS.dispatchPaceEvent(new TotemPopEvent(entity));
//                }
//            }
//        }
//    }
}
package me.ninethousand.ninehack.event;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.*;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.ClientColor;
import me.ninethousand.ninehack.feature.features.client.Chat;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.Timer;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.SPacketEntityStatus;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class EventProcessor implements NineHack.Globals {
    private final Timer logoutTimer = new Timer();
    private final AtomicBoolean tickOngoing;

    public EventProcessor() {
        this.tickOngoing = new AtomicBoolean(false);
    }

    public void init() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public boolean ticksOngoing() {
        return this.tickOngoing.get();
    }

    public void onUnload() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }


    @SubscribeEvent
    public void onUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!nullCheck() && (event.getEntity().getEntityWorld()).isRemote && event.getEntityLiving().equals(mc.player)) {
            NineHack.INVENTORY_MANAGER.update();
            for (Feature module : FeatureManager.getFeatures()) {
                if (module.isEnabled()) {
                    module.onUpdate();
                }
            }
        }
    }

    /*@SubscribeEvent
    public void onClientConnect(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.logoutTimer.reset();
        OyVey.moduleManager.onLogin();
    }

    @SubscribeEvent
    public void onClientDisconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
        OyVey.moduleManager.onLogout();
    }*/

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (nullCheck()) return;
        for (Feature module : FeatureManager.getFeatures()) {
            if (module.isEnabled()) {
                module.onUpdate();
            }
            module.onTick();
            for (Setting<?> setting : module.getSettings()) {
                if (setting.getValue() instanceof Color) {
                    Setting<Color> colorSetting = (Setting<Color>) setting;
                    if (setting.isRainbow()) {
                        colorSetting.setValue(new Color(ClientColor.colorHeightMap.get(0)));
                    }
                }
            }
        }

        for (EntityPlayer player : mc.world.playerEntities) {
            if (player == null || player.getHealth() > 0.0F)
                continue;
            MinecraftForge.EVENT_BUS.post(new DeathEvent(player));
            Chat.INSTANCE.onDeath(player);
        }
    }

    /*@SubscribeEvent
    public void onUpdateWalkingPlayer(UpdateWalkingPlayerEvent event) {
        if (fullNullCheck())
            return;
        if (event.getStage() == 0) {
            OyVey.speedManager.updateValues();
            OyVey.rotationManager.updateRotations();
            OyVey.positionManager.updatePosition();
        }
        if (event.getStage() == 1) {
            OyVey.rotationManager.restoreRotations();
            OyVey.positionManager.restorePosition();
        }
    }*/

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getStage() != 0)
            return;
        if (event.getPacket() instanceof SPacketEntityStatus) {
            SPacketEntityStatus packet = event.getPacket();
            if (packet.getOpCode() == 35 && packet.getEntity(mc.world) instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) packet.getEntity(mc.world);
                MinecraftForge.EVENT_BUS.post(new TotemPopEvent(player));
                Chat.INSTANCE.onTotemPop(player);
            }
        }
        /*if (event.getPacket() instanceof SPacketPlayerListItem && !nullCheck() && this.logoutTimer.passedS(1.0D)) {
            SPacketPlayerListItem packet = event.getPacket();
            if (!SPacketPlayerListItem.Action.ADD_PLAYER.equals(packet.getAction()) && !SPacketPlayerListItem.Action.REMOVE_PLAYER.equals(packet.getAction()))
                return;
            packet.getEntries().stream().filter(Objects::nonNull).filter(data -> (!Strings.isNullOrEmpty(data.getProfile().getName()) || data.getProfile().getId() != null))
                    .forEach(data -> {
                        String name;
                        EntityPlayer entity;
                        UUID id = data.getProfile().getId();
                        switch (packet.getAction()) {
                            case ADD_PLAYER:
                                name = data.getProfile().getName();
                                MinecraftForge.EVENT_BUS.post(new ConnectionEvent(0, id, name));
                                break;
                            case REMOVE_PLAYER:
                                entity = mc.world.getPlayerEntityByUUID(id);
                                if (entity != null) {
                                    String logoutName = entity.getName();
                                    MinecraftForge.EVENT_BUS.post(new ConnectionEvent(1, entity, id, logoutName));
                                    break;
                                }
                                MinecraftForge.EVENT_BUS.post(new ConnectionEvent(2, id, null));
                                break;
                        }
                    });
        }
        if (event.getPacket() instanceof net.minecraft.network.play.server.SPacketTimeUpdate)
            OyVey.serverManager.update();*/
    }

    @SubscribeEvent
    public void onWorldRender(RenderWorldLastEvent event) {
        if (event.isCanceled())
            return;
        mc.profiler.startSection("ninehack");
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.shadeModel(7425);
        GlStateManager.disableDepth();
        GlStateManager.glLineWidth(1.0F);
        Render3DEvent render3dEvent = new Render3DEvent(event.getPartialTicks());

        for (Feature feature : FeatureManager.getFeatures()) {
            if (feature.isEnabled()) {
                feature.onRender3D(render3dEvent);
            }
        }

        GlStateManager.glLineWidth(1.0F);
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableDepth();
        GlStateManager.enableCull();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        mc.profiler.endSection();
    }

    @SubscribeEvent
    public void renderHUD(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
            NineHack.TEXT_MANAGER.updateResolution();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onRenderGameOverlayEvent(RenderGameOverlayEvent.Text event) {
        if (event.getType().equals(RenderGameOverlayEvent.ElementType.TEXT)) {
            ScaledResolution resolution = new ScaledResolution(mc);
            Render2DEvent render2DEvent = new Render2DEvent(event.getPartialTicks(), resolution);
            for (Feature feature : FeatureManager.getFeatures()) {
                if (feature.isEnabled())
                    feature.on2DRenderEvent(render2DEvent);
            }
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (Keyboard.getEventKeyState()) {
            if (Keyboard.getEventKey() != Keyboard.KEY_NONE) {
                for (Feature module : FeatureManager.getFeatures()) {
                    if (module.getKey() == Keyboard.getEventKey()) {
                        module.toggle();
                    }
                }

                String keyName = Keyboard.getKeyName(Keyboard.getEventKey());

                // if someone presses the command prefix, it will open the chat box
                // with the key prefix already typed in (not working atm :/)
                if (keyName.equalsIgnoreCase(NineHack.CHAT_PREFIX)) {
                    mc.displayGuiScreen(new GuiChat(NineHack.CHAT_PREFIX));
                }

                try {
                    MinecraftForge.EVENT_BUS.register(event);
                } catch (Exception ignored) {}
            }
        }
    }

    /*@SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChatSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(Command.getCommandPrefix())) {
            event.setCanceled(true);
            try {
                mc.ingameGUI.getChatGUI().addToSentMessages(event.getMessage());
                if (event.getMessage().length() > 1) {
                    OyVey.commandManager.executeCommand(event.getMessage().substring(Command.getCommandPrefix().length() - 1));
                } else {
                    Command.sendMessage("Please enter a command.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Command.sendMessage(ChatFormatting.RED + "An error occurred while running this command. Check the log!");
            }
        }
    }*/

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onClientSent(ClientChatEvent event) {
        if (event.getMessage().startsWith(NineHack.CHAT_PREFIX)) {
            event.setCanceled(true);

            mc.ingameGUI.getChatGUI().addToSentMessages(event.getOriginalMessage());
            CommandManager.parseCommand(event.getMessage().replace(NineHack.CHAT_PREFIX, ""));
        } else {
            event.setMessage(event.getMessage());
        }
    }
}

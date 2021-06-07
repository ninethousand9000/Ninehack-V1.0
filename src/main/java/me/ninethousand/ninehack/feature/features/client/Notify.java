package me.ninethousand.ninehack.feature.features.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.DeathEvent;
import me.ninethousand.ninehack.event.events.PacketEvent;
import me.ninethousand.ninehack.event.events.Render2DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.NotificationManager;
import me.ninethousand.ninehack.util.ChatUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@NineHackFeature(name = "Notify", description = "Notify you when things happen", category = Category.Client)
public class Notify extends Feature {
    public static Notify INSTANCE;

    public static final Setting<Boolean> totemPop = new Setting<>("Totem Pop", false);
    public static final Setting<Boolean> autoEz = new Setting<>("AutoEZ", false);

    public static final Setting<ModuleToggleMode> moduleToggle = new Setting<>("Module Toggle", ModuleToggleMode.Chat);

    public Notify() {
        addSettings(
                totemPop,
                moduleToggle,
                autoEz
        );
    }
    public static HashMap<String, Integer> popMap = new HashMap();

    public void onDeath(EntityPlayer player) {
        if (totemPop.getValue()) {
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

    private int delayCount;
    public final ConcurrentHashMap<String, Integer> targets = new ConcurrentHashMap<>();

    @Override
    public void onEnable() {
        this.delayCount = 0;
    }

    @Override
    public void onUpdate() {
        delayCount++;
        for (Entity entity : mc.world.getLoadedEntityList()) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (player.getHealth() <= 0) {
                    if (targets.containsKey(player.getName())) {
                        this.announceDeath(player.getName());
                        this.targets.remove(player.getName());
                    }
                }
            }
        }
        targets.forEach((name, timeout) -> {
            if (timeout <= 0) {
                targets.remove(name);
            } else {
                targets.put(name, timeout - 1);
            }

        });
    }

    @SubscribeEvent
    public void onAttackEntity(AttackEntityEvent event) {
        if (event.getTarget() instanceof EntityPlayer) {
            this.targets.put(event.getTarget().getName(), 20);
        }
    }

    @SubscribeEvent
    public void onSendAttackPacket(PacketEvent.Send event) {
        CPacketUseEntity packet;
        if (event.getPacket() instanceof CPacketUseEntity && (packet = event.getPacket()).getAction() == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(mc.world) instanceof EntityPlayer) {
            this.targets.put(Objects.requireNonNull(packet.getEntityFromWorld(mc.world)).getName(), 20);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(DeathEvent event) {
        if (this.targets.containsKey(event.player.getName())) {
            if (autoEz.getValue()) {
                this.announceDeath(event.player.getName());
            }
            this.targets.remove(event.player.getName());
        }
    }

    private void announceDeath(String name) {
        if (this.delayCount < 150) return;
        delayCount = 0;
        mc.player.sendChatMessage("hahaha so ez " + name + "! next time use ninehack");
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

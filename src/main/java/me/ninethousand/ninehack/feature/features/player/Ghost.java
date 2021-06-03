package me.ninethousand.ninehack.feature.features.player;

import me.ninethousand.ninehack.event.events.MoveEvent;
import me.ninethousand.ninehack.event.events.PacketEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@NineHackFeature(name = "Ghostcam", description = "Become caspar (freecam)", category = Category.Player)
public class Ghost extends Feature {
    public static Feature INSTANCE;
    public NumberSetting<Double> speed = new NumberSetting<>("Speed", 0.1D, 1.0D, 3.0D, 1);
    public Setting<Boolean> packet = new Setting<>("Cancel Packet", false);

    private double posX, posY, posZ;
    private float pitch, yaw;

    private EntityOtherPlayerMP clonedPlayer;

    private boolean isRidingEntity;
    private Entity ridingEntity;

    public Ghost() {
        addSettings(packet, speed);
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            isRidingEntity = mc.player.getRidingEntity() != null;

            if (mc.player.getRidingEntity() == null) {
                posX = mc.player.posX;
                posY = mc.player.posY;
                posZ = mc.player.posZ;
            } else {
                ridingEntity = mc.player.getRidingEntity();
                mc.player.dismountRidingEntity();
            }

            pitch = mc.player.rotationPitch;
            yaw = mc.player.rotationYaw;

            clonedPlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
            clonedPlayer.copyLocationAndAnglesFrom(mc.player);
            clonedPlayer.rotationYawHead = mc.player.rotationYawHead;
            mc.world.addEntityToWorld(-100, clonedPlayer);
            mc.player.capabilities.isFlying = true;
            mc.player.capabilities.setFlySpeed((float) (speed.getValue() / 100f));
            mc.player.noClip = true;
        }
    }

    @Override
    public void onDisable() {
        EntityPlayer localPlayer = mc.player;
        if (localPlayer != null) {
            mc.player.setPositionAndRotation(posX, posY, posZ, yaw, pitch);
            mc.world.removeEntityFromWorld(-100);
            clonedPlayer = null;
            posX = posY = posZ = 0.D;
            pitch = yaw = 0.f;
            mc.player.capabilities.isFlying = false;
            mc.player.capabilities.setFlySpeed(0.05f);
            mc.player.noClip = false;
            mc.player.motionX = mc.player.motionY = mc.player.motionZ = 0.f;

            if (isRidingEntity) {
                mc.player.startRiding(ridingEntity, true);
            }
        }
    }

    @Override
    public void onUpdate() {
        mc.player.capabilities.isFlying = true;
        mc.player.capabilities.setFlySpeed((float) (speed.getValue() / 100f));
        mc.player.noClip = true;
        mc.player.onGround = false;
        mc.player.fallDistance = 0;
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        mc.player.noClip = true;
    };

    @SubscribeEvent
    public void onPlayerPushOutOfBlock(PlayerSPPushOutOfBlocksEvent event) {
        event.setCanceled(true);
    }

    @SubscribeEvent
    public void onPacketSend(PacketEvent event) {
        if ((event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketInput) && packet.getValue()) {
            event.setCanceled(true);
        }
    }
}

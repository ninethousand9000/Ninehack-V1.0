package me.ninethousand.ninehack.feature.features.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.Render2DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.mixin.accessors.game.IMinecraft;
import me.ninethousand.ninehack.util.ColorUtil;
import me.ninethousand.ninehack.util.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.Objects;

@NineHackFeature(name = "HUD", description = "Based HUD", category = Category.Client)
public class HUD extends Feature {
    public static Feature INSTANCE;

    private static ScaledResolution res = new ScaledResolution(mc);
    private static final ItemStack totem = new ItemStack(Items.TOTEM_OF_UNDYING);

    public static final Setting<Boolean> rainbow = new Setting<>("Rainbow", true);
    public static final Setting<Boolean> rolling = new Setting<>("Rolling", true);
    public static final NumberSetting<Integer> factor = new NumberSetting<>("Rolling Factor", 0, 10, 100, 1);
    public static final NumberSetting<Integer> hue = new NumberSetting<>("Hue", 0, 255, 255, 1);
    public static final NumberSetting<Integer> saturation = new NumberSetting<>("Saturation", 0, 255, 255, 1);
    public static final NumberSetting<Integer> brightness = new NumberSetting<>("Brightness", 0, 255, 255, 1);

    public static final Setting<Boolean> watermark = new Setting<>("Watermark", true);
    public static final Setting<WatermarkMode> watermarkMode = new Setting<>("Watermark Mode", WatermarkMode.Default);
    public static final NumberSetting<Integer> watermarkX = new NumberSetting<>("Watermark X", 1, 1, 960, 1);
    public static final NumberSetting<Integer> watermarkY = new NumberSetting<>("Watermark Y", 1, 1, 530, 1);

    public static final Setting<Boolean> ping = new Setting<>("Ping", true);
    public static final NumberSetting<Integer> pingX = new NumberSetting<>("Ping X", 1, 1, 960, 1);
    public static final NumberSetting<Integer> pingY = new NumberSetting<>("Ping Y", 1, 10, 530, 1);

    public static final Setting<Boolean> welcomer = new Setting<>("Welcomer", true);
    public static final Setting<WelcomeMode> welcomerMode = new Setting<>("Welcomer Mode", WelcomeMode.Default);
    public static final NumberSetting<Integer> welcomerX = new NumberSetting<>("Welcomer X", 1, 480, 960, 1);
    public static final NumberSetting<Integer> welcomerY = new NumberSetting<>("Welcomer Y", 1, 1, 530, 1);

    public static final Setting<Boolean> coords = new Setting<>("Coords", true);
    public static final NumberSetting<Integer> coordX = new NumberSetting<>("Coords X", 1, 1, 960, 1);
    public static final NumberSetting<Integer> coordY = new NumberSetting<>("Coords Y", 1, 530, 530, 1);

    public static final Setting<Boolean> totems = new Setting<>("Totem", true);

    public static final Setting<Boolean> armor = new Setting<>("Armor", true);
    public static final Setting<Boolean> percent = new Setting<>("Armor %", true);

    public HUD() {
        addSettings(
                rainbow,
                rolling,
                factor,
                hue,
                saturation,
                brightness,
                watermark,
                watermarkMode,
                watermarkX,
                watermarkY,
                ping,
                pingX,
                pingY,
                welcomer,
                welcomerMode,
                welcomerX,
                welcomerY,
                coords,
                coordX,
                coordY,
                totems,
                armor,
                percent
        );
    }

    @Override
    public void onUpdate() {
        if (rainbow.getValue()) {
            if (hue.getValue() < 255) {
                hue.setValue(hue.getValue() + 1);
            }
            else {
                hue.setValue(0);
            }
        }
    }

    @Override
    public void on2DRenderEvent(Render2DEvent event) {
        if (watermark.getValue()) drawWatermark(watermarkX.getValue(), watermarkY.getValue());
        if (ping.getValue()) drawPing(pingX.getValue(), pingY.getValue());
        if (welcomer.getValue()) drawWelcomer(welcomerX.getValue(), welcomerY.getValue());
        if (coords.getValue()) drawCoords(coordX.getValue(), coordY.getValue());
        if (totems.getValue()) drawTotem();
        if (armor.getValue()) drawArmor(percent.getValue());


    }

    public void drawWatermark(int x, int y) {
        String watermark = "Ninehack";

        if (watermarkMode.getValue() == WatermarkMode.Default) {
            watermark = NineHack.NAME_VERSION;
        }
        else if (watermarkMode.getValue() == WatermarkMode.Tesco) {
            if (((IMinecraft)mc).getIntegratedServerIsRunning()) {
                watermark = NineHack.NAME_VERSION + " : " + mc.player.getName() + " : " + "Singleplayer";
            }
            else {
                watermark = NineHack.NAME_VERSION + " : " + mc.player.getName() + " : " + Objects.requireNonNull(mc.getCurrentServerData()).serverIP;
            }
        }

        drawText(watermark, x, y);
    }

    public void drawPing(int x, int y) {
        String text = "Ping " + TextFormatting.WHITE + (!mc.isSingleplayer() ? mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() : -1) + " ms";
        drawText(text, x, y);
    }

    public void drawWelcomer(int x, int y) {
        String text = "Hello";

        switch (welcomerMode.getValue()) {
            case Default:
                text = "Welcome, " + mc.player.getName() + " :^)";
        }

        drawText(text, x, y);
    }

    public void drawCoords(int x, int y) {
        final boolean inHell = HUD.mc.world.getBiome(HUD.mc.player.getPosition()).getBiomeName().equals("Hell");
        final int posX = (int) HUD.mc.player.posX;
        final int posY = (int) HUD.mc.player.posY;
        final int posZ = (int) HUD.mc.player.posZ;
        final float nether = inHell ? 8.0f : 0.125f;
        final int hposX = (int) (HUD.mc.player.posX * nether);
        final int hposZ = (int) (HUD.mc.player.posZ * nether);

        final String coordinates = "XYZ "  + posX + ", " + posY + ", " + posZ + " " + "[" + hposX + ", " + hposZ + "]";

        drawText(coordinates, x, y);
    }

    public void drawText(String text, int x, int y) {
        if (rolling.getValue()) NineHack.TEXT_MANAGER.drawRainbowString(text, x, y, Color.HSBtoRGB(hue.getValue() / 255f, saturation.getValue() / 255f, brightness.getValue() / 255f), factor.getValue(), ClientFont.shadow.getValue());
        else NineHack.TEXT_MANAGER.drawStringWithShadow(text, x, y, Color.HSBtoRGB(hue.getValue() / 255f, saturation.getValue() / 255f, brightness.getValue() / 255f));
    }

    public void drawTotem() {
        final int width = NineHack.TEXT_MANAGER.scaledWidth;
        final int height = NineHack.TEXT_MANAGER.scaledHeight;
        int totems = HUD.mc.player.inventory.mainInventory.stream().filter(itemStack -> itemStack.getItem() == Items.TOTEM_OF_UNDYING).mapToInt(ItemStack::getCount).sum();
        if (HUD.mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) {
            totems += HUD.mc.player.getHeldItemOffhand().getCount();
        }
        if (totems > 0) {
            GlStateManager.enableTexture2D();
            final int i = width / 2;
            final int iteration = 0;
            final int y = height - 55 - ((HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            final int x = i - 189 + 180 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(HUD.totem, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, HUD.totem, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            drawText(totems + "", x + 19 - NineHack.TEXT_MANAGER.getStringWidth(totems + ""), y + 9);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }

    public void drawArmor(final boolean percent) {
        final int width = NineHack.TEXT_MANAGER.scaledWidth;
        final int height = NineHack.TEXT_MANAGER.scaledHeight;
        GlStateManager.enableTexture2D();
        final int i = width / 2;
        int iteration = 0;
        final int y = height - 55 - ((HUD.mc.player.isInWater() && HUD.mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
        for (final ItemStack is : HUD.mc.player.inventory.armorInventory) {
            ++iteration;
            if (is.isEmpty()) {
                continue;
            }
            final int x = i - 90 + (9 - iteration) * 20 + 2;
            GlStateManager.enableDepth();
            RenderUtil.itemRender.zLevel = 200.0f;
            RenderUtil.itemRender.renderItemAndEffectIntoGUI(is, x, y);
            RenderUtil.itemRender.renderItemOverlayIntoGUI(HUD.mc.fontRenderer, is, x, y, "");
            RenderUtil.itemRender.zLevel = 0.0f;
            GlStateManager.enableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
            NineHack.TEXT_MANAGER.drawStringWithShadow(s, (float) (x + 19 - 2 - NineHack.TEXT_MANAGER.getStringWidth(s)), (float) (y + 9), 16777215);
            if (!percent) {
                continue;
            }
            int dmg = 0;
            final int itemDurability = is.getMaxDamage() - is.getItemDamage();
            final float green = (is.getMaxDamage() - (float) is.getItemDamage()) / is.getMaxDamage();
            final float red = 1.0f - green;
            if (percent) {
                dmg = 100 - (int) (red * 100.0f);
            } else {
                dmg = itemDurability;
            }
            NineHack.TEXT_MANAGER.drawStringWithShadow(dmg + "", (float) (x + 8 - NineHack.TEXT_MANAGER.getStringWidth(dmg + "") / 2), (float) (y - 11), ColorUtil.toRGBA((int) (red * 255.0f), (int) (green * 255.0f), 0));
        }
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
    }

    public enum WatermarkMode {
        Default,
        Tesco
    }

    public enum WelcomeMode {
        Default,
    }}

package me.ninethousand.ninehack.feature.features.visual;

import me.ninethousand.ninehack.event.events.Render3DEvent;
import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.setting.NumberSetting;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.util.BlockUtil;
import me.ninethousand.ninehack.util.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.awt.*;

@NineHackFeature(name = "HoleESP", description = "Based Hoe", category = Category.Visual)
public class HoleESP extends Feature {
    public static Feature INSTANCE;

    public static final Setting<Boolean> own = new Setting<>("Own Hole", true);
    public static final Setting<Boolean> fov = new Setting<>("In FOV", true);
    public static final NumberSetting<Integer> range = new NumberSetting<>("Range", 0, 5, 10, 1);
    public static final NumberSetting<Integer> rangeY = new NumberSetting<>("Vert Range", 0, 5, 10, 1);
    public static final Setting<Boolean> box = new Setting<>("Box", true);
    public static final Setting<Boolean> gradient = new Setting<>("Gradient", true);
    public static final Setting<Boolean> invert = new Setting<>("Invert", true);
    public static final Setting<Boolean> outline = new Setting<>("Outline", true);
    public static final Setting<Boolean> gradientOutline = new Setting<>("Gradient Outline", true);
    public static final Setting<Boolean> invertOutline = new Setting<>("Invert Outline", true);
    public static final NumberSetting<Float> height = new NumberSetting<>("Height", -2.0f, 0.0f, 2.0f, 1);
    public static final NumberSetting<Float> outlineWidth = new NumberSetting<>("Outline Width", 0.0f, 1.0f, 4.0f, 1);
    public static final NumberSetting<Integer> alpha = new NumberSetting<>("Alpha", 0, 155, 255, 1);
    public static final Setting<Color> bedrockColor = new Setting<>("Bedrock Color", new Color(0x921DC45D, true));
    public static final Setting<Color> bedrockOColor = new Setting<>("Bedrock Line Color", new Color(0xF7159544, true));
    public static final Setting<Color> obsidianColor = new Setting<>("Obby Color", new Color(0x92C4231D, true));
    public static final Setting<Color> obsidianOColor = new Setting<>("Obby Color", new Color(0xD07B1812, true));

    private int currentAlpha = 0;

    public HoleESP() {
        addSettings(
                own,
                fov,
                range,
                rangeY,
                box,
                gradient,
                invert,
                outline,
                gradientOutline,
                invertOutline,
                height,
                outlineWidth,
                alpha,
                bedrockColor,
                bedrockOColor,
                obsidianColor,
                obsidianOColor
        );
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        assert (HoleESP.mc.getRenderViewEntity() != null);
        Vec3i playerPos = new Vec3i(HoleESP.mc.getRenderViewEntity().posX, HoleESP.mc.getRenderViewEntity().posY, HoleESP.mc.getRenderViewEntity().posZ);
        for (int x = playerPos.getX() - range.getValue(); x < playerPos.getX() + range.getValue(); ++x) {
            for (int z = playerPos.getZ() - range.getValue(); z < playerPos.getZ() + range.getValue(); ++z) {
                for (int y = playerPos.getY() + rangeY.getValue(); y > playerPos.getY() - rangeY.getValue(); --y) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!HoleESP.mc.world.getBlockState(pos).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 1, 0)).getBlock().equals(Blocks.AIR) || !HoleESP.mc.world.getBlockState(pos.add(0, 2, 0)).getBlock().equals(Blocks.AIR) || pos.equals(new BlockPos(HoleESP.mc.player.posX, HoleESP.mc.player.posY, HoleESP.mc.player.posZ)) && !own.getValue().booleanValue() || !BlockUtil.isPosInFov(pos).booleanValue() && fov.getValue().booleanValue())
                        continue;
                    if (HoleESP.mc.world.getBlockState(pos.north()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.east()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.west()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.south()).getBlock() == Blocks.BEDROCK && HoleESP.mc.world.getBlockState(pos.down()).getBlock() == Blocks.BEDROCK) {
                        RenderUtil.drawBoxESP(pos, bedrockColor.getValue(), outline.getValue(), bedrockOColor.getValue(), outlineWidth.getValue(), outline.getValue(), box.getValue(), alpha.getValue(), true, height.getValue(), gradient.getValue(), gradientOutline.getValue(), invert.getValue(), invertOutline.getValue(), currentAlpha);
                        continue;
                    }
                    if (!BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.down()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.east()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.west()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.south()).getBlock()) || !BlockUtil.isBlockUnSafe(HoleESP.mc.world.getBlockState(pos.north()).getBlock()))
                        continue;
                    RenderUtil.drawBoxESP(pos, obsidianColor.getValue(), outline.getValue(), obsidianOColor.getValue(), outlineWidth.getValue(), outline.getValue(), box.getValue(), alpha.getValue(), true, height.getValue(), gradient.getValue(), gradientOutline.getValue(), invert.getValue(), invertOutline.getValue(), currentAlpha);
                }
            }
        }
    }


}

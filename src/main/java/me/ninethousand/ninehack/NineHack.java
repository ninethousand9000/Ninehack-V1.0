package me.ninethousand.ninehack;

import com.olliem5.pace.handler.EventHandler;
import me.ninethousand.ninehack.util.font.CFontRenderer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.awt.*;

@Mod(modid = NineHack.MOD_ID, name = NineHack.MOD_NAME, version = NineHack.MOD_VERSION)
public class NineHack {
    public static final String MOD_ID = "ninehack";
    public static final String MOD_NAME = "NineHack";
    public static final String MOD_VERSION = "0.1";
    public static final String MOD_EDITION = "Pre-Release";
    public static final String APP_ID = "847411619914711061";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;
    public static String CHAT_PREFIX = ",";

    public static final EventHandler EVENT_BUS = new EventHandler();
    public static final Logger LOGGER = LogManager.getLogger("ninehack");
    public static final CFontRenderer FONT_RENDERER = new CFontRenderer(new Font("Montserrat", Font.PLAIN, 18), true, true);

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("ninethousand.dev");

        StartUp.start();
    }

    public static void log(String message) {
        LOGGER.info(message);
    }

    public interface Globals {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
    }
}

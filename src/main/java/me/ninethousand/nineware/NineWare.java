package me.ninethousand.nineware;

import com.olliem5.pace.handler.EventHandler;
import me.ninethousand.nineware.managers.FeatureManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

@Mod(modid = NineWare.MOD_ID, name = NineWare.MOD_NAME, version = NineWare.MOD_VERSION)
public class NineWare {
    public static final String MOD_ID = "9kware";
    public static final String MOD_NAME = "9kWare";
    public static final String MOD_VERSION = "0.1";
    public static final String MOD_EDITION = "Pre-Release";
    public static final String APP_ID = "839542596329799696";
    public static final String NAME_VERSION = MOD_NAME + " " + MOD_VERSION;
    public static String CHAT_PREFIX = ",";

    public static final EventHandler EVENT_BUS = new EventHandler();
    public static final Logger LOGGER = LogManager.getLogger("ninek");

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        Display.setTitle("9kWare V1");

        StartUp.start();
    }

    public static void log(String message) {
        LOGGER.info(message);
    }

    public interface Minecraft {
        net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
    }
}

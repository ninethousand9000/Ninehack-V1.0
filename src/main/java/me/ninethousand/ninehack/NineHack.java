package me.ninethousand.ninehack;

import me.ninethousand.ninehack.event.EventProcessor;
import me.ninethousand.ninehack.feature.gui.menu.CustomMainMenu;
import me.ninethousand.ninehack.managers.InventoryManager;
import me.ninethousand.ninehack.managers.TextManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import java.awt.*;

/**
 * @author 9k
 * @since May 26th 2021
 * @version 1.0.0 Beta
 * @apiNote Hello
 */

@Mod(modid = NineHack.MOD_ID, name = NineHack.MOD_NAME, version = NineHack.MOD_VERSION)
public class NineHack {
    public static final String MOD_ID = "ninehack";
    public static final String MOD_NAME = "NineHack";
    public static final String MOD_VERSION = "1.0.0";
    public static final String MOD_EDITION = "Pre-Release";
    public static final String APP_ID = "847411619914711061";
    public static final String NAME_VERSION = MOD_NAME + " v" + MOD_VERSION;
    public static String CHAT_PREFIX = ",";
    public static final int DEFAULT_GUI_KEY = Keyboard.KEY_I;

//    public static final EventHandler EVENT_BUS = new EventHandler();
    public static final EventProcessor EVENT_PROCESSOR = new EventProcessor();
    public static final Logger LOGGER = LogManager.getLogger("ninehack");
    public static final TextManager TEXT_MANAGER = new TextManager();
    public static final CustomMainMenu CUSTOM_MAIN_MENU = new CustomMainMenu();
    public static final InventoryManager INVENTORY_MANAGER = new InventoryManager();

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

package me.ninethousand.ninehack;

import me.ninethousand.ninehack.event.EventProcessor;
import me.ninethousand.ninehack.feature.command.ToggleCommand;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.common.MinecraftForge;

public class StartUp {
    public static void start() {
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        NineHack.EVENT_BUS.register(new EventProcessor());

        FeatureManager.init();
        NineHack.log("Features Initialised");

        initCommandManager();
        NineHack.log("Commands Initialised");
    }

    private static void initCommandManager() {
        CommandManager.addCommands(
                new ToggleCommand()
        );
    }
}

package me.ninethousand.nineware;

import me.ninethousand.nineware.event.EventProcessor;
import me.ninethousand.nineware.feature.command.ToggleCommand;
import me.ninethousand.nineware.managers.FeatureManager;
import me.yagel15637.venture.manager.CommandManager;
import net.minecraftforge.common.MinecraftForge;

public class StartUp {
    public static void start() {
        MinecraftForge.EVENT_BUS.register(new EventProcessor());
        NineWare.EVENT_BUS.register(new EventProcessor());

        FeatureManager.init();
        NineWare.log("Features Initialised");

        initCommandManager();
        NineWare.log("Commands Initialised");
    }

    private static void initCommandManager() {
        CommandManager.addCommands(
                new ToggleCommand()
        );
    }
}

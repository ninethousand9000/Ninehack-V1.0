package me.ninethousand.ninehack;

import me.ninethousand.ninehack.feature.command.ToggleCommand;
import me.ninethousand.ninehack.feature.features.client.RPC;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.RPCUtil;
import me.yagel15637.venture.manager.CommandManager;

public class StartUp {
    public static void start() {
        NineHack.EVENT_PROCESSOR.init();
        NineHack.log("Events Initialised");

        FeatureManager.init();
        NineHack.log("Features Initialised");

        initCommandManager();
        NineHack.log("Commands Initialised");

        NineHack.CUSTOM_MAIN_MENU.initGui();

        if (RPC.INSTANCE.isEnabled()) {
            RPCUtil.startup();
        }
    }

    private static void initCommandManager() {
        CommandManager.addCommands(
                new ToggleCommand()
        );
    }
}

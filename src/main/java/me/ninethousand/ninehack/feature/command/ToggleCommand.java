package me.ninethousand.ninehack.feature.command;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.managers.FeatureManager;
import me.ninethousand.ninehack.util.ChatUtil;
import me.yagel15637.venture.command.AbstractCommand;

public final class ToggleCommand extends AbstractCommand {
    public ToggleCommand() {
        super("Toggles a specified module.", "toggle/t/ [module name]", "toggle", "t");
    }

    @Override
    public void execute(String[] args) {
        if (args.length == 0) return;

        for (String string : args) {
            for (Feature module : FeatureManager.getFeatures()) {
                if (module.getName().equalsIgnoreCase(string)) {
                    module.toggle();
                }
            }
        }
    }
}

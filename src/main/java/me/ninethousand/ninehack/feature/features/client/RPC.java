package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.util.RPCUtil;

@NineHackFeature(name = "RPC", description = "Discord RPC", category = Category.Client)
public class RPC extends Feature {
    public static Feature INSTANCE;

    @Override
    public void onEnable() {
        if (nullCheck()) return;

        RPCUtil.startup();
    }

    @Override
    public void onDisable() {
        if (nullCheck()) return;

        RPCUtil.shutdown();
    }
}

package me.ninethousand.ninehack.feature.features.client;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.annotation.EnabledByDefault;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;

@EnabledByDefault
@NineHackFeature(name = "Friends", description = "Manage Friends", category = Category.Client)
public class Friends extends Feature{
    public static Feature INSTANCE;
}

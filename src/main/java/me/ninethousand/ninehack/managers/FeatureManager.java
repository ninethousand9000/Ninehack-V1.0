package me.ninethousand.ninehack.managers;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.*;
import me.ninethousand.ninehack.feature.features.combat.Burrow;
import me.ninethousand.ninehack.feature.features.movement.AntiWeb;
import me.ninethousand.ninehack.feature.features.player.Ghost;
import me.ninethousand.ninehack.feature.features.player.PearlBind;
import me.ninethousand.ninehack.feature.features.visual.*;
import me.ninethousand.ninehack.feature.features.movement.Sprint;

import java.util.ArrayList;
import java.util.Arrays;

public final class FeatureManager {
    private static final ArrayList<Feature> features = new ArrayList<>();

    public static void init() {
        features.addAll(Arrays.asList(
                // Combat Features
                Burrow.INSTANCE = new Burrow(),

                // Movement Features
                Sprint.INSTANCE = new Sprint(),
                AntiWeb.INSTANCE = new AntiWeb(),

                // Player Features
                PearlBind.INSTANCE = new PearlBind(),
                Ghost.INSTANCE = new Ghost(),

                // Other Features


                // Visual Features
                Blur.INSTANCE = new Blur(),
                FOV.INSTANCE = new FOV(),
                Swing.INSTANCE = new Swing(),
                WireESP.INSTANCE = new WireESP(),
                Menu.INSTANCE = new Menu(),
                HoleESP.INSTANCE = new HoleESP(),
                FutureChams.INSTANCE = new FutureChams(),
                BreakESP.INSTANCE = new BreakESP(),

                // Client Features
                GUI.INSTANCE = new GUI(),
                HUD.INSTANCE = new HUD(),
                CustomFont.INSTANCE = new CustomFont(),
                ClientColor.INSTANCE = new ClientColor(),
                RPC.INSTANCE = new RPC(),
                Chat.INSTANCE = new Chat(),
                Friends.INSTANCE = new Friends()
        ));

        features.sort(FeatureManager::alphabetize);
    }

    private static int alphabetize(Feature feature1, Feature feature2) {
        return feature1.getName().compareTo(feature2.getName());
    }

    public static ArrayList<Feature> getFeatures() {
        return features;
    }

    public static ArrayList<Feature> getFeaturesInCategory(Category category) {
        ArrayList<Feature> featuresInCategory = new ArrayList<>();

        for (Feature feature : features) {
            if (feature.getCategory().equals(category)) {
                featuresInCategory.add(feature);
            }
        }

        return featuresInCategory;
    }

    public static Feature getFeatureByName(String name) {
        return features.stream()
                .filter(feature -> feature.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }
}


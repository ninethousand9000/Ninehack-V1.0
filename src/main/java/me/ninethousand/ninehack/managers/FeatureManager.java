package me.ninethousand.ninehack.managers;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.client.*;
import me.ninethousand.ninehack.feature.features.combat.Burrow;
import me.ninethousand.ninehack.feature.features.movement.AntiWeb;
import me.ninethousand.ninehack.feature.features.other.SliderTest;
import me.ninethousand.ninehack.feature.features.player.Ghost;
import me.ninethousand.ninehack.feature.features.player.PearlBind;
import me.ninethousand.ninehack.feature.features.visual.Blur;
import me.ninethousand.ninehack.feature.features.movement.Sprint;
import me.ninethousand.ninehack.feature.features.visual.FOV;
import me.ninethousand.ninehack.feature.features.visual.Swing;
import me.ninethousand.ninehack.feature.features.visual.WireESP;

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
                SliderTest.INSTANCE = new SliderTest(),

                // Visual Features
                Blur.INSTANCE = new Blur(),
                FOV.INSTANCE = new FOV(),
                Swing.INSTANCE = new Swing(),
                WireESP.INSTANCE = new WireESP(),

                // Client Features
                GUI.INSTANCE = new GUI(),
                HUD.INSTANCE = new HUD(),
                ClientFont.INSTANCE = new ClientFont(),
                ClientColor.INSTANCE = new ClientColor(),
                RPC.INSTANCE = new RPC(),
                Notify.INSTANCE = new Notify()
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


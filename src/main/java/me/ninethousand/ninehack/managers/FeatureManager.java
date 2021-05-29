package me.ninethousand.ninehack.managers;

import me.ninethousand.ninehack.feature.Category;
import me.ninethousand.ninehack.feature.Feature;
import me.ninethousand.ninehack.feature.features.visual.Blur;
import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.features.movement.Sprint;
import me.ninethousand.ninehack.feature.features.visual.FOV;
import me.ninethousand.ninehack.feature.features.visual.Swing;

import java.util.ArrayList;
import java.util.Arrays;

public final class FeatureManager {
    private static final ArrayList<Feature> features = new ArrayList<>();

    public static void init() {
        features.addAll(Arrays.asList(
                GUI.INSTANCE,
                Sprint.INSTANCE,
                Blur.INSTANCE,
                Swing.INSTANCE,
                FOV.INSTANCE
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

    public static Feature getFeatureByClazz(Class clazz) {
        return features.stream()
                .filter(feature -> feature.getClazz().equals(clazz))
                .findFirst()
                .orElse(null);
    }
}


package me.ninethousand.nineware.managers;

import me.ninethousand.nineware.feature.Category;
import me.ninethousand.nineware.feature.Feature;
import me.ninethousand.nineware.feature.features.client.GUI;
import me.ninethousand.nineware.feature.features.movement.Sprint;
import me.ninethousand.nineware.feature.features.other.Test1;
import me.ninethousand.nineware.feature.features.other.Test2;
import me.ninethousand.nineware.feature.features.other.Test3;

import java.util.ArrayList;
import java.util.Arrays;

public final class FeatureManager {
    private static final ArrayList<Feature> features = new ArrayList<>();

    public static void init() {
        features.addAll(Arrays.asList(
                new GUI(),
                new Sprint(),
                new Test1(),
                new Test2(),
                new Test3()
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


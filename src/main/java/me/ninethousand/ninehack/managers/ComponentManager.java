package me.ninethousand.ninehack.managers;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.feature.hud.components.Component;
import me.ninethousand.ninehack.feature.hud.components.textcomponent.TextComponent;
import me.ninethousand.ninehack.feature.hud.components.textcomponent.textcomponents.Watermark;

import java.util.ArrayList;
import java.util.Arrays;

public class ComponentManager {
    private static final ArrayList<Component> components = new ArrayList<>();
    private static final ArrayList<TextComponent> textComponents = new ArrayList<>();

    public static void init() {
        components.addAll(Arrays.asList(
                Watermark.INSTANCE = new Watermark(NineHack.NAME_VERSION, Component.Priority.Maximum, Component.Corner.TopLeft)
        ));

        for (Component component : ComponentManager.components) {
            if (component instanceof TextComponent) {
                textComponents.add((TextComponent) component);
            }
         }
    }

    public static ArrayList<Component> getComponents() {
        return components;
    }

    public static ArrayList<TextComponent> getTextComponents() {
        return textComponents;
    }

    public static ArrayList<TextComponent> getTextComponentsByCorner(Component.Corner corner) {
        ArrayList<TextComponent> textComponents = new ArrayList<>();

        for (TextComponent component : ComponentManager.textComponents) {
            if (component.getCorner() == corner) {
                textComponents.add(component);
            }
        }

        return textComponents;
    }


}

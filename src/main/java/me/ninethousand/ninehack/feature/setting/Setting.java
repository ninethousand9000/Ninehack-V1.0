package me.ninethousand.ninehack.feature.setting;

import java.awt.*;
import java.util.ArrayList;

public class Setting<T> {
    private final String name;

    private T value;

    private boolean isOpened;

    private boolean isTyping;

    private boolean rainbow;


    private float alpha = 0.2f;

    private final ArrayList<Setting<?>> subSettings = new ArrayList<>();

    public Setting(String name,  T value) {
        this.name = name;
        this.value = value;
    }

    public Setting(Setting<?> parent, String name, T value) {
        this.name = name;
        this.value = value;

        if (parent.getValue() instanceof Boolean) {
            Setting<Boolean> booleanSetting = (Setting<Boolean>) parent;
            booleanSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Enum) {
            Setting<Enum<?>> enumSetting = (Setting<Enum<?>>) parent;
            enumSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Color) {
            Setting<Color> colorSetting = (Setting<Color>) parent;
            colorSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Integer) {
            NumberSetting<Integer> integerNumberSetting = (NumberSetting<Integer>) parent;
            integerNumberSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Double) {
            NumberSetting<Double> doubleNumberSetting = (NumberSetting<Double>) parent;
            doubleNumberSetting.addSubSetting(this);
        }

        if (parent.getValue() instanceof Float) {
            NumberSetting<Float> floatNumberSetting = (NumberSetting<Float>) parent;
            floatNumberSetting.addSubSetting(this);
        }
    }

    public ArrayList<Setting<?>> getSubSettings() {
        return this.subSettings;
    }

    public boolean hasSubSettings() {
        return this.subSettings.size() > 0;
    }

    public void addSubSetting(Setting<?> subSetting) {
        this.subSettings.add(subSetting);
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    public boolean isOpened() {
        return isOpened;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public void setOpened(boolean opened) {
        isOpened = opened;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public boolean isRainbow() {
        return rainbow;
    }

    public void setRainbow(boolean rainbow) {
        this.rainbow = rainbow;
    }
}

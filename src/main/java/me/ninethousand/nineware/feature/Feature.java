package me.ninethousand.nineware.feature;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.nineware.NineWare;
import me.ninethousand.nineware.feature.setting.Setting;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Feature implements NineWare.Minecraft {
    private final String name = getAnnotation().name();
    private final String description = getAnnotation().description();
    private final Category category = getAnnotation().category();

    private int key = getAnnotation().key();

    private boolean enabled = false;
    private boolean opened = false;
    private boolean binding = false;
    private boolean drawn = true;

    private final ArrayList<Setting<?>> settings = new ArrayList<>();

    private NineWareFeature getAnnotation() {
        if (getClass().isAnnotationPresent(NineWareFeature.class)) {
            return getClass().getAnnotation(NineWareFeature.class);
        }

        throw new IllegalStateException("Annotation 'NineWareFeature' not found!");
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void enable() {
        enabled = true;

        NineWare.EVENT_BUS.register(this);

        onEnable();

        handleNotifications(true);
    }

    public void disable() {
        enabled = false;

        NineWare.EVENT_BUS.unregister(this);

        onDisable();

        handleNotifications(false);
    }

    private void handleNotifications(boolean enable) {
        if (nullCheck()) return;

        String message;
        String narratorMessage;

        if (enable) {
            message = ChatFormatting.BLUE + "[" + ChatFormatting.DARK_AQUA + name + ChatFormatting.BLUE + "]" +  ChatFormatting.GREEN + " enabled";
            narratorMessage = name + " is now enabled";
        } else {
            message = ChatFormatting.BLUE + "[" + ChatFormatting.DARK_AQUA + name + ChatFormatting.BLUE + "]" + ChatFormatting.RED + " disabled";
            narratorMessage = name + " is now disabled";
        }
    }

    public void toggle() {
        try {
            if (enabled) {
                disable();
            } else {
                enable();
            }
        } catch (Exception ignored) {}
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            enable();
        } else {
            disable();
        }
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public int getKey() {
        return key;
    }

    public boolean isOpened() {
        return opened;
    }

    public boolean isBinding() {
        return binding;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
    }

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public boolean hasSettings() {
        return this.settings.size() > 0;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public void setDrawn(boolean drawn) {
        this.drawn = drawn;
    }

    public String getArraylistInfo() {
        return "";
    }

    public void onEnable() {}

    public void onDisable() {}

    public void onUpdate() {}

}

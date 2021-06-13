package me.ninethousand.ninehack.feature;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.event.events.Render2DEvent;
import me.ninethousand.ninehack.event.events.Render3DEvent;
import me.ninethousand.ninehack.feature.annotation.AlwaysEnabled;
import me.ninethousand.ninehack.feature.annotation.EnabledByDefault;
import me.ninethousand.ninehack.feature.annotation.NineHackFeature;
import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.features.client.Chat;
import me.ninethousand.ninehack.feature.gui.notifications.Notification;
import me.ninethousand.ninehack.feature.gui.notifications.NotificationType;
import me.ninethousand.ninehack.feature.setting.Setting;
import me.ninethousand.ninehack.managers.NotificationManager;
import me.ninethousand.ninehack.util.ChatUtil;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Feature implements NineHack.Globals {
    private final String name = getAnnotation().name();
    private final String description = getAnnotation().description();
    private final Category category = getAnnotation().category();

    private int key = getAnnotation().key();

    public final boolean alwaysEnabled = this.getClass().isAnnotationPresent(AlwaysEnabled.class);
    public final boolean enabledByDefault = this.getClass().isAnnotationPresent(EnabledByDefault.class);

    private boolean enabled = alwaysEnabled || enabledByDefault;
    private boolean opened = false;
    private boolean binding = false;
    private boolean drawn = true;

    private final ArrayList<Setting<?>> settings = new ArrayList<>();

    public Feature() {
        if (this.alwaysEnabled) {

        }
    }

    private NineHackFeature getAnnotation() {
        if (getClass().isAnnotationPresent(NineHackFeature.class)) {
            return getClass().getAnnotation(NineHackFeature.class);
        }

        throw new IllegalStateException("Annotation 'NineWareFeature' not found!");
    }

    public boolean nullCheck() {
        return mc.player == null || mc.world == null;
    }

    public void enable() {
        enabled = true;

        MinecraftForge.EVENT_BUS.register(this);

        onEnable();

        handleNotifications(true);
    }

    public void disable() {
        enabled = alwaysEnabled ? true : false;

        MinecraftForge.EVENT_BUS.register(this);

        onDisable();

        handleNotifications(false);
    }

    private void handleNotifications(boolean enable) {
        if (nullCheck()) return;

        if (Chat.moduleToggle.getValue() != Chat.ModuleToggleMode.None) {
            String message;
            if (this.getClass() != GUI.class)
                if (Chat.moduleToggle.getValue() == Chat.ModuleToggleMode.Chat) {
                    if (enable) {
                        message =  ChatFormatting.GREEN + name + " enabled.";
                    } else {
                        message = ChatFormatting.RED + name + " disabled.";
                    }
                    ChatUtil.sendClientMessage(message);
                }
                else if (Chat.moduleToggle.getValue() == Chat.ModuleToggleMode.HUD) {
                    if (enable) {
                        message =  name + " enabled.";
                    } else {
                        message = name + " disabled.";
                    }

                    NotificationManager.show(new Notification(NotificationType.INFO, "Module Toggle", message, 1));
                }
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

    public void addSettings(Setting<?>... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public void removeSettings(Setting<?>... settings) {
        this.settings.removeAll(Arrays.asList(settings));
    }

    public ArrayList<Setting<?>> getSettings() {
        return settings;
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

    public void onTick() {}

    public void on2DRenderEvent(Render2DEvent event) {}

    public void onRender3D(Render3DEvent event) {}



}

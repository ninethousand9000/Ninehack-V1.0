package me.ninethousand.ninehack.feature.gui.notifications;

import me.ninethousand.ninehack.NineHack;
import me.ninethousand.ninehack.util.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;

public class Notification implements NineHack.Globals {
    private NotificationType type;
    private String title;
    private String messsage;
    private long start;

    private long fadedIn;
    private long fadeOut;
    private long end;

    public Notification(NotificationType type, String title, String messsage, int length) {
        this.type = type;
        this.title = title;
        this.messsage = messsage;

        fadedIn = 200 * length;
        fadeOut = fadedIn + 500 * length;
        end = fadeOut + fadedIn;
    }

    public void show() {
        start = System.currentTimeMillis();
    }

    public boolean isShown() {
        return getTime() <= end;
    }

    private long getTime() {
        return System.currentTimeMillis() - start;
    }

    public void render() {
        ScaledResolution res = new ScaledResolution(mc);
        double offset = 0;
        int width = 120;
        int height = 30;
        long time = getTime();

        if (time < fadedIn) {
            offset = Math.tanh(time / (double) (fadedIn) * 3.0) * width;
        } else if (time > fadeOut) {
            offset = (Math.tanh(3.0 - (time - fadeOut) / (double) (end - fadeOut) * 3.0) * width);
        } else {
            offset = width;
        }

        Color color = new Color(0, 0, 0, 220);
        Color color1;

        if (type == NotificationType.INFO)
            color1 = new Color(0, 26, 169);
        else if (type == NotificationType.WARNING)
            color1 = new Color(204, 193, 0);
        else {
            color1 = new Color(204, 0, 18);
            int i = Math.max(0, Math.min(255, (int) (Math.sin(time / 100.0) * 255.0 / 2 + 127.5)));
            color = new Color(i, 0, 0, 220);
        }

        RenderUtil.drawRect(res.getScaledWidth() - offset, res.getScaledHeight() - 5 - height, res.getScaledWidth(), res.getScaledHeight() - 5, color);
        RenderUtil.drawRect(res.getScaledWidth() - offset, res.getScaledHeight() - 5 - height, res.getScaledWidth() - offset + 4, res.getScaledHeight() - 5, color1);

        NineHack.TEXT_MANAGER.drawStringWithShadow(title, (float) (res.getScaledWidth() - offset + 8), res.getScaledHeight() - 2 - height, -1);
        NineHack.TEXT_MANAGER.drawStringWithShadow(messsage, (float) (res.getScaledWidth() - offset + 8), res.getScaledHeight() - 15, -1);


    }
}

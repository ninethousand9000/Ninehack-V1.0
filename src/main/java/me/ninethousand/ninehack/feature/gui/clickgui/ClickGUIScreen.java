package me.ninethousand.ninehack.feature.gui.clickgui;

import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.feature.gui.clickgui.theme.Theme;
import me.ninethousand.ninehack.feature.gui.clickgui.theme.themes.NineHackTheme;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;

public class ClickGUIScreen extends GuiScreen {
    public static NineHackTheme theme = new NineHackTheme();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        theme.draw(mouseX, mouseY);

        theme.leftClicked = false;
        theme.rightClicked = false;
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0) {
            theme.leftClicked = true;
            theme.leftDown = true;
        }

        if (mouseButton == 1) {
            theme.rightClicked = true;
            theme.rightDown = true;
        }
    }

    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            theme.leftClicked = false;
            theme.leftDown = false;
        }

        if (state == 1) {
            theme.rightClicked = false;
            theme.rightDown = false;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);

        theme.keyDown = keyCode;
    }

    @Override
    public void onGuiClosed() {
        GUI.guiOpen = false;
        GUI.INSTANCE.setEnabled(false);
    }
}

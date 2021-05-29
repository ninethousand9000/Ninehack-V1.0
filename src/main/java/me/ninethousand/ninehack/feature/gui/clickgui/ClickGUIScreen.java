package me.ninethousand.ninehack.feature.gui.clickgui;

import me.ninethousand.ninehack.feature.features.client.GUI;
import me.ninethousand.ninehack.managers.FeatureManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class ClickGUIScreen extends GuiScreen {
    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ClickGUI.drawGUI(0, 0, mouseX, mouseY);

        ClickGUI.leftClicked = false;
        ClickGUI.rightClicked = false;
    }

    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0) {
            ClickGUI.leftClicked = true;
            ClickGUI.leftDown = true;
        }

        if (mouseButton == 1) {
            ClickGUI.rightClicked = true;
            ClickGUI.rightDown = true;
        }
    }

    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            ClickGUI.leftClicked = false;
            ClickGUI.leftDown = false;
        }

        if (state == 1) {
            ClickGUI.rightClicked = false;
            ClickGUI.rightDown = false;
        }
    }

    @Override
    public void onGuiClosed() {
        GUI.guiOpen = false;
    }
}

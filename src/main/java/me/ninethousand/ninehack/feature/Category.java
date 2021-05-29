package me.ninethousand.ninehack.feature;

public enum Category {
    Combat,
    Movement,
    Player,
    Visual,
    Other,
    Client;

    private boolean openInGui = true;

    public boolean isOpenInGui() {
        return openInGui;
    }

    public void setOpenInGui(final boolean openInGui) {
        this.openInGui = openInGui;
    }
}

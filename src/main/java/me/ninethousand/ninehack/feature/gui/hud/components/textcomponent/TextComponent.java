package me.ninethousand.ninehack.feature.gui.hud.components.textcomponent;

import me.ninethousand.ninehack.feature.gui.hud.components.Component;

public abstract class TextComponent extends Component {
    private String text;
    private Priority priority;
    private Corner corner;

    private boolean drawn;

    public TextComponent(String text, Priority priority, Corner corner) {
        this.text = text;
        this.priority = priority;
        this.corner = corner;
    }

    public boolean isDrawn() {
        return drawn;
    }

    public Corner getCorner() {
        return corner;
    }
}

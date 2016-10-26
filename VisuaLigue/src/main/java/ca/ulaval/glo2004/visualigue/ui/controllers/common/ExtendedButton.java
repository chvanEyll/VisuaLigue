package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.scene.control.Button;

public class ExtendedButton extends Button {

    private static final String SELECTED_STYLE_CLASS = "selected";
    private Boolean selected = false;

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        if (selected) {
            this.getStyleClass().add(SELECTED_STYLE_CLASS);
        } else {
            this.getStyleClass().remove(SELECTED_STYLE_CLASS);
        }
        this.selected = selected;
    }

}

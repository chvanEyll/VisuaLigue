package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;

public class ExtendedButton extends Button {

    private static final String SELECTED_STYLE_CLASS = "selected";
    private Boolean selected = false;
    private BooleanProperty layoutReadyProperty = new SimpleBooleanProperty(false);

    public ExtendedButton() {
        this.widthProperty().addListener(this::onLayoutReady);
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        if (this.selected.equals(selected)) {
            return;
        }
        if (selected) {
            this.getStyleClass().add(SELECTED_STYLE_CLASS);
        } else {
            this.getStyleClass().remove(SELECTED_STYLE_CLASS);
        }
        this.selected = selected;
    }

    public void onLayoutReady(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        if (oldPropertyValue.intValue() == 0 && newPropertyValue.intValue() != 0) {
            layoutReadyProperty.set(true);
        }
    }

    public BooleanProperty layoutReadyProperty() {
        return layoutReadyProperty;
    }

}

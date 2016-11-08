package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class ExtendedButton extends Button {

    private static final String SELECTED_STYLE_CLASS = "selected";
    private BooleanProperty selectedProperty = new SimpleBooleanProperty(false);
    private BooleanProperty layoutReadyProperty = new SimpleBooleanProperty(false);
    private Boolean autoSelect = false;

    public ExtendedButton() {
        this.widthProperty().addListener(this::onLayoutReady);
        this.selectedProperty().addListener(this::onSelectedPropertyChanged);
        this.setOnMouseClicked(this::onMouseClicked);
    }

    public Boolean getAutoSelect() {
        return autoSelect;
    }

    public void setAutoSelect(Boolean autoSelect) {
        this.autoSelect = autoSelect;
    }

    public Boolean isSelected() {
        return selectedProperty.get();
    }

    public void setSelected(Boolean selected) {
        if (selected) {
            this.getStyleClass().add(SELECTED_STYLE_CLASS);
        } else {
            this.getStyleClass().remove(SELECTED_STYLE_CLASS);
        }
    }

    private void onMouseClicked(MouseEvent e) {
        if (autoSelect) {
            selectedProperty.set(!selectedProperty.get());
        }
    }

    public void onSelectedPropertyChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
        setSelected(newPropertyValue);
    }

    public BooleanProperty selectedProperty() {
        return selectedProperty;
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

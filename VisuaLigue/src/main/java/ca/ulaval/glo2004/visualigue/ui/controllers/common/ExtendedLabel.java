package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class ExtendedLabel extends Label {

    private BooleanProperty layoutReadyProperty = new SimpleBooleanProperty(false);

    public ExtendedLabel() {
        this.widthProperty().addListener(this::onLayoutReady);
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

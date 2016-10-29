package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import javafx.beans.value.ObservableValue;
import javafx.scene.layout.TilePane;

public class ExtendedTilePane extends TilePane {

    public ExtendedTilePane() {
        this.widthProperty().addListener(this::widthChangedListener);
    }

    public void widthChangedListener(ObservableValue<? extends Number> value, Number oldPropertyValue, Number newPropertyValue) {
        this.setPrefTileWidth(Math.floor(newPropertyValue.doubleValue() / this.getPrefColumns()));
    }

}

package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import javafx.application.Platform;
import javafx.scene.layout.TilePane;

public class ExtendedTilePane extends TilePane {

    @Override
    public void resize(double width, double height) {
        this.setPrefTileWidth(Math.floor(width / this.getPrefColumns()));
        super.resize(width, height);
        Platform.runLater(() -> {
            this.getParent().autosize();
        });
    }

}

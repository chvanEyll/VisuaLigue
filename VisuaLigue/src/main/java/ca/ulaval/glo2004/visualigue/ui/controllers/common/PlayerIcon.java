package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
import javafx.scene.paint.Color;

public class PlayerIcon extends SvgImage {

    private static final String COLOR_CSS_STYLE_NAME = "#innerCircle";
    private static final String SVG_PATH_NAME = "/images/player-icon.fxml";
    private Color color;

    public PlayerIcon() {
        this.setUrl(SVG_PATH_NAME);
    }

    public void setColor(Color color) {
        if (this.color == null || !this.color.equals(color)) {
            this.color = color;
            this.lookup(COLOR_CSS_STYLE_NAME).setStyle(String.format("-fx-fill: %s", FXUtils.colorToHex(color)));
        }
    }

}

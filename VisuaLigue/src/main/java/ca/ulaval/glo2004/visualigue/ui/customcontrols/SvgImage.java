package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class SvgImage extends StackPane {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (url != null) {
            this.url = url;
            updateSvgImage();
        }
    }

    private void updateSvgImage() {
        try {
            Node svgRootNode = FXMLLoader.load(getClass().getResource(url));
            this.getStylesheets().clear();
            this.getStylesheets().add(url + ".css");
            this.getChildren().add(svgRootNode);
        } catch (IOException ex) {
            Logger.getLogger(SvgImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

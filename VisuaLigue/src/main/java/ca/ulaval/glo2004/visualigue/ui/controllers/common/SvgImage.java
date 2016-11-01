package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import javafx.scene.layout.StackPane;

public class SvgImage extends StackPane {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (this.url == null || !this.url.equals(url)) {
            this.url = url;
            updateSvgImage();
        }
    }

    private void updateSvgImage() {
        if (url == null) {
            this.getChildren().clear();
        } else {
            setVectorImage(url);
        }
    }

    private void setVectorImage(String url) {
        View view = InjectableFXMLLoader.loadView(url);
        this.getStylesheets().clear();
        this.getStylesheets().add(url + ".css");
        this.getChildren().clear();
        this.getChildren().add(view.getRoot());
    }

}

package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LinkButton extends HBox {

    public static final String VIEW_NAME = "/views/custom-controls/link-button.fxml";
    @FXML SvgImage svgImage;
    @FXML Label textLabel;
    private String text;
    private String imageUrl;

    public LinkButton() {
        try {
            FXMLLoader fxmlLoader = InjectableFXMLLoader.createLoader(VIEW_NAME);
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(Breadcrumb.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        textLabel.setText(text);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        svgImage.setUrl(imageUrl);
    }

}

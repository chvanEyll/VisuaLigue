package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class LinkButton extends HBox {

    public static final String VIEW_NAME = "/views/customcontrols/link-button.fxml";
    @FXML private SvgImage svgImage;
    @FXML private Label textLabel;
    private String text;
    private String imageUrl;

    public LinkButton() {
        try {
            FXMLLoader fxmlLoader = InjectableFXMLLoader.createLoader(VIEW_NAME, this, this);
            fxmlLoader.load();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
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

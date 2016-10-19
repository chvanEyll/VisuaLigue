package ca.ulaval.glo2004.visualigue.ui.customcontrols;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BreadcrumbItem extends HBox {

    public static final String VIEW_NAME = "/views/customcontrols/breadcrumb-item.fxml";
    @FXML private Label titleLabel;
    @FXML private HBox navigationArrow;
    @FXML private HBox item;
    public EventHandler<Object> onClick = new EventHandler<>();

    public BreadcrumbItem(String title, Boolean showArrow) {
        try {
            FXMLLoader fxmlLoader = InjectableFXMLLoader.createLoader(VIEW_NAME, this, this);
            fxmlLoader.load();
            titleLabel.setText(title);
            FXUtils.setDisplay(navigationArrow, showArrow);
        } catch (IOException ex) {
            Logger.getLogger(BreadcrumbItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setActive(Boolean active) {
        if (!active) {
            item.getStyleClass().remove("active");
        } else {
            item.getStyleClass().add("active");
        }
    }

    @FXML
    private void onClick(MouseEvent e) {
        onClick.fire(this, null);
    }

}

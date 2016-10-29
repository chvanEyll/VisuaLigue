package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class BreadcrumbItem extends HBox {

    public static final String VIEW_NAME = "/views/common/breadcrumb-item.fxml";
    @FXML private Label titleLabel;
    @FXML private HBox navigationArrow;
    @FXML private HBox item;
    public EventHandler onClick = new EventHandler();

    public BreadcrumbItem(String title, Boolean showArrow) {
        InjectableFXMLLoader.loadView(VIEW_NAME, this, this);
        titleLabel.setText(title);
        FXUtils.setDisplay(navigationArrow, showArrow);
    }

    public void setActive(Boolean active) {
        if (!active) {
            item.getStyleClass().remove("active");
        } else {
            item.getStyleClass().add("active");
        }
    }

    @FXML
    protected void onMouseClicked(MouseEvent e) {
        onClick.fire(this);
    }

}

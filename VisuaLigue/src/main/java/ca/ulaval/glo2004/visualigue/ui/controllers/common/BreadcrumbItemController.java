package ca.ulaval.glo2004.visualigue.ui.controllers.common;

import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class BreadcrumbItemController {

    public static final String VIEW_NAME = "/views/common/breadcrumb-item.fxml";

    @FXML private Label titleLabel;
    @FXML private HBox navigationArrow;
    @FXML private HBox item;
    private String title;
    public EventHandler<Object> onClick = new EventHandler<>();

    public void init(String title, Boolean showArrow) {
        this.title = title;
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
    private void onClick() {
        onClick.fire(this, null);
    }

}

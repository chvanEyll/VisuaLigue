package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement.ObstacleManagementController;
import ca.ulaval.glo2004.visualigue.ui.controllers.settings.SettingsController;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement.SportManagementController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MainMenuController {

    public static final String VIEW_NAME = "/views/main-menu.fxml";
    private static final double MENU_PANE_COLLAPSED_WIDTH = 58.0;
    private static final double MENU_PANE_EXTENDED_WIDTH = 170.0;

    @FXML private VBox menuPane;
    @FXML private HBox playsMenuItem;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox obstaclesMenuItem;
    @FXML private HBox settingsMenuItem;
    private boolean isMenuPaneCollapsed = true;
    public EventHandler<FXMLLoader> onMenuClicked = new EventHandler<>();

    public void init() {
        menuPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
    }

    public void toggleOpen() {
        if (!isMenuPaneCollapsed) {
            collapseMenuPane();
        } else {
            expandMenuPane();
        }
    }

    private void collapseMenuPane() {
        PredefinedAnimations.regionCollapse(menuPane, MENU_PANE_COLLAPSED_WIDTH);
        isMenuPaneCollapsed = true;
    }

    private void expandMenuPane() {
        PredefinedAnimations.regionExpand(menuPane, MENU_PANE_EXTENDED_WIDTH);
        isMenuPaneCollapsed = false;
    }

    @FXML
    public void onPlaysMenuItemClicked() {

    }

    @FXML
    public void onSportsMenuItemClicked() {
        processMenuClick(SportManagementController.VIEW_NAME, sportsMenuItem);
    }

    @FXML
    public void onObstaclesMenuItemClicked() {
        processMenuClick(ObstacleManagementController.VIEW_NAME, obstaclesMenuItem);
    }

    @FXML
    public void onSettingsMenuItemClicked() {
        processMenuClick(SettingsController.VIEW_NAME, settingsMenuItem);
    }

    private void processMenuClick(String viewName, HBox menuItem) {
        unselectAllMenus();
        menuItem.getStyleClass().add("active");
        onMenuClicked.fire(this, InjectableFXMLLoader.load(viewName));
    }

    private void unselectAllMenus() {
        menuPane.getChildren().forEach(n -> {
            n.getStyleClass().remove("active");
        });
    }
}

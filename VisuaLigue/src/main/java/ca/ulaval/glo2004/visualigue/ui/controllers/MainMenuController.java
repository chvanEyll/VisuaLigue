package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.animation.Animation;
import ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement.ObstacleManagementController;
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
    private boolean isMenuPaneCollapsed = false;
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
            extendMenuPane();
        }
    }

    private void collapseMenuPane() {
        Animation.method(menuPane::setClip).duration(0.4).from(menuPane.getClip()).to(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE)).groupBegin().easeOutExp();
        Animation.method(menuPane::setPrefWidth).duration(0.4).from(menuPane.getPrefWidth()).to(MENU_PANE_COLLAPSED_WIDTH).easeOutExp();
        Animation.method(menuPane::setMinWidth).duration(0.4).from(menuPane.getMinWidth()).to(MENU_PANE_COLLAPSED_WIDTH).groupEnd().easeOutExp();
        isMenuPaneCollapsed = true;
    }

    private void extendMenuPane() {
        Animation.method(menuPane::setPrefWidth).duration(0.4).from(menuPane.getPrefWidth()).to(MENU_PANE_EXTENDED_WIDTH).groupBegin().easeOutExp();
        Animation.method(menuPane::setMinWidth).duration(0.4).from(menuPane.getMinWidth()).to(MENU_PANE_EXTENDED_WIDTH).easeOutExp();
        Animation.method(menuPane::setClip).duration(0.4).from(menuPane.getClip()).to(new Rectangle(MENU_PANE_EXTENDED_WIDTH, Integer.MAX_VALUE)).groupEnd().easeOutExp();
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

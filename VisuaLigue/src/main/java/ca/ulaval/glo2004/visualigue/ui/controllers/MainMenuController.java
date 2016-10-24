package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.ui.controllers.obstaclemanagement.ObstacleManagementController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement.PlayManagementController;
import ca.ulaval.glo2004.visualigue.ui.controllers.settings.SettingsController;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement.SportManagementController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MainMenuController {

    public static final String VIEW_NAME = "/views/main-menu.fxml";
    private static final String ACTIVE_CSS_STYLE_NAME = "active";
    private static final double MENU_PANE_COLLAPSED_WIDTH = 58.0;
    private static final double MENU_PANE_EXTENDED_WIDTH = 170.0;

    @FXML private VBox menuPane;
    @FXML private HBox playsMenuItem;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox obstaclesMenuItem;
    @FXML private HBox settingsMenuItem;
    private boolean isMenuPaneCollapsed = true;
    public EventHandler<View> onMenuClicked = new EventHandler();

    public void init() {
        menuPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        selectMenu(PlayManagementController.VIEW_NAME, playsMenuItem);
    }

    public void toggleExpand() {
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
    protected void onPlaysMenuItemClicked(MouseEvent e) {
        selectMenu(PlayManagementController.VIEW_NAME, playsMenuItem);
    }

    @FXML
    protected void onSportsMenuItemClicked(MouseEvent e) {
        selectMenu(SportManagementController.VIEW_NAME, sportsMenuItem);
    }

    @FXML
    protected void onObstaclesMenuItemClicked(MouseEvent e) {
        selectMenu(ObstacleManagementController.VIEW_NAME, obstaclesMenuItem);
    }

    @FXML
    protected void onSettingsMenuItemClicked(MouseEvent e) {
        selectMenu(SettingsController.VIEW_NAME, settingsMenuItem);
    }

    private void selectMenu(String viewName, HBox menuItem) {
        if (!menuItem.getStyleClass().contains(ACTIVE_CSS_STYLE_NAME)) {
            unselectAllMenus();
            menuItem.getStyleClass().add(ACTIVE_CSS_STYLE_NAME);
            onMenuClicked.fire(this, InjectableFXMLLoader.loadView(viewName));
        }
    }

    private void unselectAllMenus() {
        menuPane.getChildren().forEach(menuItem -> {
            menuItem.getStyleClass().remove(ACTIVE_CSS_STYLE_NAME);
        });
    }
}

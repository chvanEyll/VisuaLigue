package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportlist.SportListController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MainMenuController {

    public static final String VIEW_NAME = "/views/main-menu.fxml";
    private static final int MENU_PANE_COLLAPSED_WIDTH = 58;
    private static final int MENU_PANE_EXTENDED_WIDTH = 170;

    @FXML private VBox menuPane;
    @FXML private HBox playsMenuItem;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox obstaclesMenuItem;
    @FXML private HBox settingsMenuItem;
    private boolean isMenuPaneCollapsed = false;
    public EventHandler<FXMLLoader> onMenuClicked = new EventHandler<>();

    public void init() {
        collapseMenuPane();
    }

    public void toggleOpen() {
        if (!isMenuPaneCollapsed) {
            collapseMenuPane();
        } else {
            extendMenuPane();
        }
    }

    private void collapseMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        isMenuPaneCollapsed = true;
    }

    private void extendMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setClip(null);
        isMenuPaneCollapsed = false;
    }

    @FXML
    public void onPlaysMenuItemClicked() {

    }

    @FXML
    public void onSportsMenuItemClicked() {
        processMenuClick(SportListController.VIEW_NAME, sportsMenuItem);
    }

    @FXML
    public void onObstaclesMenuItemClicked() {

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

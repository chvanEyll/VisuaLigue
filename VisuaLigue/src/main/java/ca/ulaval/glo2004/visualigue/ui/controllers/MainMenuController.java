package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportlist.SportListController;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MainMenuController {

    public static final String VIEW_NAME = "/views/main-menu.fxml";
    private static final int MENU_PANE_COLLAPSED_WIDTH = 58;
    private static final int MENU_PANE_EXTENDED_WIDTH = 170;

    @FXML private VBox menuPane;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox playsMenuItem;
    private Pane logoPane;
    private boolean isMenuPaneCollapsed = false;
    public EventHandler<FXMLLoader> onMenuClicked = new EventHandler<>();

    public void init(Pane logoPane) {
        this.logoPane = logoPane;
        collapseMenuPane();
    }

    @FXML
    public void onHamburgerIconClick() {
        if (!isMenuPaneCollapsed) {
            collapseMenuPane();
        } else {
            extendMenuPane();
        }
    }

    private void collapseMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        logoPane.setPrefWidth(MENU_PANE_COLLAPSED_WIDTH);
        logoPane.setMinWidth(MENU_PANE_COLLAPSED_WIDTH);
        menuPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        logoPane.setClip(new Rectangle(MENU_PANE_COLLAPSED_WIDTH, Integer.MAX_VALUE));
        isMenuPaneCollapsed = true;
    }

    private void extendMenuPane() {
        menuPane.setPrefWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setMinWidth(MENU_PANE_EXTENDED_WIDTH);
        logoPane.setPrefWidth(MENU_PANE_EXTENDED_WIDTH);
        logoPane.setMinWidth(MENU_PANE_EXTENDED_WIDTH);
        menuPane.setClip(null);
        logoPane.setClip(null);
        isMenuPaneCollapsed = false;
    }

    @FXML
    public void onSportsMenuItemClick() {
        processMenuClick(SportListController.VIEW_NAME, sportsMenuItem);
    }

    @FXML
    public void onPlaysMenuItemClick() {

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

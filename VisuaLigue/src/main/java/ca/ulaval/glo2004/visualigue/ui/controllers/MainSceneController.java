package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.GuiceFXMLLoader;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class MainSceneController implements Initializable {

    public static final String VIEW_NAME = "/views/main.fxml";
    private static final int MENU_PANE_COLLAPSED_WIDTH = 58;
    private static final int MENU_PANE_EXTENDED_WIDTH = 170;

    @FXML private VBox menuPane;
    @FXML private Pane logoPane;
    @FXML private HBox sportsMenuItem;
    @FXML private HBox playsMenuItem;
    @FXML private Pane contentPane;
    private FXMLLoader mainViewLoader;
    private boolean isMenuPaneCollapsed = false;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        collapseMenuPane();

    }

    @FXML
    private void onHamburgerIconClick() {
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
    private void onSportsMenuItemClick() {
        setMainView(GuiceFXMLLoader.load(SportListController.VIEW_NAME), sportsMenuItem);
    }

    @FXML
    private void onPlaysMenuItemClick() {

    }

    private void setMainView(FXMLLoader fxmlLoader, HBox menuItem) {
        unselectAllMenus();
        menuItem.getStyleClass().add("active");
        setView(fxmlLoader);
        mainViewLoader = fxmlLoader;
    }

    private void setView(FXMLLoader fxmlLoader) {
        Controller controller = fxmlLoader.getController();
        controller.onViewChangeRequest.setHandler(this::onViewChangeRequestHandler);
        controller.onViewCloseRequest.setHandler(this::onViewCloseRequestHandler);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(fxmlLoader.getRoot());
    }

    private void onViewChangeRequestHandler(Object sender, FXMLLoader fxmlLoader) {
        setView(fxmlLoader);
    }

    private void onViewCloseRequestHandler(Object sender, Object eventArgs) {
        setView(mainViewLoader);
    }

    private void unselectAllMenus() {
        menuPane.getChildren().stream().forEach(n -> {
            n.getStyleClass().remove("active");
        });
    }
}

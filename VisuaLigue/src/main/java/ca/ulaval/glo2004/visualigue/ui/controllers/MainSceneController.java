package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainSceneController implements Initializable {

    public static final String VIEW_NAME = "/views/main.fxml";

    @FXML private Pane contentPane;
    @FXML private Button previousButton;
    @FXML private Button titleEditButton;
    @FXML private Label sectionTitleLabel;
    @FXML private Pane sectionTitleSpacer;
    @FXML private MainMenuController mainMenuController;
    private ViewFlow viewFlow = new ViewFlow();
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenuController.onMenuClicked.setHandler(this::onMainMenuClicked);
        mainMenuController.init();
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    private void onMainMenuClicked(Object sender, View requestedView) {
        setMainView(requestedView);
    }

    @FXML
    public void onMenuToggleClicked(MouseEvent e) {
        mainMenuController.toggleOpen();
    }

    @FXML
    public void onPreviousButtonAction(ActionEvent e) {
        previousView();
    }

    private void setMainView(View view) {
        viewFlow.clear();
        viewFlow = new ViewFlow(view);
        setView(view);
    }

    private void onViewChangeRequested(Object sender, View view) {
        nextView(view);
    }

    private void onViewCloseRequested(Object sender, Object eventArgs) {
        previousView();
    }

    private void nextView(View view) {
        viewFlow.addView(view);
        setView(view);
    }

    private void previousView() {
        if (viewFlow.count() > 1) {
            View view = viewFlow.moveToPrevious();
            setView(view);
        }
    }

    private void setView(View view) {
        ControllerBase controller = (ControllerBase) view.getController();
        controller.onViewChangeRequested.setHandler(this::onViewChangeRequested);
        controller.onViewCloseRequested.setHandler(this::onViewCloseRequested);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(view.getRoot());
        sectionTitleLabel.textProperty().bindBidirectional(controller.getTitle());
        FXUtils.setDisplay(previousButton, viewFlow.count() > 1);
        FXUtils.setDisplay(sectionTitleSpacer, viewFlow.count() <= 1);
        FXUtils.setDisplay(titleEditButton, controller.isTitleEditable());
        PredefinedAnimations.nodeZoom(view.getRoot());
    }
}

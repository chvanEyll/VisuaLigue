package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.animation.PredefinedAnimations;
import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainSceneController extends ControllerBase {

    public static final String VIEW_NAME = "/views/main.fxml";

    @FXML private Pane contentPane;
    @FXML private Button previousButton;
    @FXML private Button viewTitleEditButton;
    @FXML private Button viewTitleValidateButton;
    @FXML private Label viewTitleLabel;
    @FXML private TextField viewTitleTextField;
    @FXML private Pane viewTitleSpacer;
    @FXML private MainMenuController mainMenuController;
    private ViewFlow viewFlow = new ViewFlow();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenuController.onMenuClicked.setHandler(this::onMainMenuClicked);
        mainMenuController.init();
    }

    private void onMainMenuClicked(Object sender, View requestedView) {
        setMainView(requestedView);
    }

    @FXML
    protected void onMenuToggleClicked(MouseEvent e) {
        mainMenuController.toggleExpand();
    }

    @FXML
    protected void onPreviousButtonAction(ActionEvent e) {
        previousView();
    }

    private void setMainView(View view) {
        if (validateCurrentViewClose()) {
            viewFlow.clear();
            viewFlow = new ViewFlow(view);
            setView(view);
        }
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
        if (viewFlow.count() > 1 && validateCurrentViewClose()) {
            View view = viewFlow.moveToPrevious();
            setView(view);
        }
    }

    private void setView(View view) {
        ControllerBase controller = view.getController();
        controller.onViewChangeRequested.setHandler(this::onViewChangeRequested);
        controller.onViewCloseRequested.setHandler(this::onViewCloseRequested);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(view.getRoot());
        viewTitleLabel.textProperty().bindBidirectional(controller.getTitle());
        viewTitleTextField.textProperty().bindBidirectional(controller.getTitle());
        FXUtils.setDisplay(previousButton, viewFlow.count() > 1);
        FXUtils.setDisplay(viewTitleSpacer, viewFlow.count() <= 1);
        FXUtils.setDisplay(viewTitleEditButton, controller.isTitleEditable());
        FXUtils.setDisplay(viewTitleValidateButton, false);
        FXUtils.setDisplay(viewTitleTextField, false);
        PredefinedAnimations.nodeZoom(view.getRoot());
    }

    private Boolean validateCurrentViewClose() {
        if (!viewFlow.empty()) {
            View currentView = viewFlow.getCurrentView();
            return currentView.getController().onViewClosing();
        } else {
            return true;
        }
    }

    @FXML
    protected void onViewTitleEditButtonAction(ActionEvent e) {
        toggleViewTitleEditionMode(true);
    }

    @FXML
    protected void onViewTitleTextFieldAction(ActionEvent e) {
        toggleViewTitleEditionMode(false);
    }

    @FXML
    protected void onViewTitleValidateButtonAction(ActionEvent e) {
        toggleViewTitleEditionMode(false);
    }

    public void onViewTitleTextFieldFocusChanged(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
        if (!newPropertyValue) {
            toggleViewTitleEditionMode(false);
        }
    }

    private void toggleViewTitleEditionMode(Boolean editionMode) {
        FXUtils.setDisplay(viewTitleTextField, editionMode);
        FXUtils.setDisplay(viewTitleValidateButton, editionMode);
        FXUtils.setDisplay(viewTitleLabel, !editionMode);
        FXUtils.setDisplay(viewTitleEditButton, !editionMode);
        if (editionMode) {
            viewTitleTextField.focusedProperty().addListener(this::onViewTitleTextFieldFocusChanged);
            FXUtils.requestFocusDelayed(viewTitleTextField);
        }
    }
}

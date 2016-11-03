package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.utils.javafx.BindingUtils;
import ca.ulaval.glo2004.visualigue.utils.javafx.FXUtils;
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
    private static final String EDITABLE_TITLE_CSS_STYLE_NAME = "editable";
    @FXML private Button previousButton;
    @FXML private Button viewTitleEditButton;
    @FXML private Button viewTitleValidateButton;
    @FXML private Label viewTitleLabel;
    @FXML private TextField viewTitleTextField;
    @FXML private Pane viewTitleSpacer;
    @FXML private MainMenuController mainMenuController;
    @FXML private ViewFlowController viewFlowController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenuController.onViewFlowResetRequested.setHandler(this::onViewFlowResetRequested);
        mainMenuController.init();
        viewTitleTextField.focusedProperty().addListener(this::onViewTitleTextFieldFocusChanged);
        super.addChild(mainMenuController);
    }

    @Override
    public void clean() {
        BindingUtils.clearBindings(viewTitleLabel.textProperty());
        BindingUtils.clearBindings(viewTitleTextField.textProperty());
    }

    @FXML
    protected void onMenuToggleClicked(MouseEvent e) {
        mainMenuController.toggleExpand();
    }

    @FXML
    protected void onPreviousButtonAction(ActionEvent e) {
        previousView();
    }

    private void onViewCloseRequested(Object sender, ViewFlowRequestEventArgs e) {
        if (!previousView()) {
            e.cancel();
        }
    }

    private void onViewChangeRequested(Object sender, ViewFlowRequestEventArgs e) {
        if (!changeView(e.getView())) {
            e.cancel();
        }
    }

    private void onViewAppendRequested(Object sender, ViewFlowRequestEventArgs e) {
        appendView(e.getView());
    }

    private void onViewFlowResetRequested(Object sender, ViewFlowRequestEventArgs e) {
        if (!resetViewFlow(e.getView())) {
            e.cancel();
        }
    }

    private void appendView(View view) {
        viewFlowController.appendView(view);
        initView(view);
    }

    private Boolean changeView(View view) {
        try {
            viewFlowController.popView();
            viewFlowController.appendView(view);
            initView(view);
            return true;
        } catch (ViewFlowException ex) {
            return false;
        }
    }

    private Boolean previousView() {
        try {
            viewFlowController.popView();
            initView(viewFlowController.getCurrentView());
            return true;
        } catch (ViewFlowException ex) {
            return false;
        }
    }

    private Boolean resetViewFlow(View view) {
        try {
            viewFlowController.clear();
            viewFlowController.appendView(view);
            initView(view);
            return true;
        } catch (ViewFlowException ex) {
            return false;
        }
    }

    private void initView(View view) {
        ControllerBase controller = (ControllerBase) view.getController();
        controller.onViewAppendRequested.setHandler(this::onViewAppendRequested);
        controller.onViewChangeRequested.setHandler(this::onViewChangeRequested);
        controller.onViewCloseRequested.setHandler(this::onViewCloseRequested);
        BindingUtils.cleanBindBidirectional(viewTitleLabel.textProperty(), controller.getTitle());
        BindingUtils.cleanBindBidirectional(viewTitleTextField.textProperty(), controller.getTitle());
        FXUtils.setDisplay(previousButton, viewFlowController.count() > 1);
        FXUtils.setDisplay(viewTitleSpacer, viewFlowController.count() <= 1);
        FXUtils.setDisplay(viewTitleEditButton, controller.isTitleEditable());
        if (controller.isTitleEditable()) {
            viewTitleLabel.getStyleClass().add(EDITABLE_TITLE_CSS_STYLE_NAME);
        } else {
            viewTitleLabel.getStyleClass().remove(EDITABLE_TITLE_CSS_STYLE_NAME);
        }
        FXUtils.setDisplay(viewTitleValidateButton, false);
        FXUtils.setDisplay(viewTitleTextField, false);
    }

    @Override
    public Boolean onClose() {
        try {
            viewFlowController.clear();
            return true;
        } catch (ViewFlowException ex) {
            return false;
        }
    }

    @FXML
    protected void onViewTitleLabelMouseClicked(MouseEvent e) {
        if (((ControllerBase) viewFlowController.getCurrentView().getController()).isTitleEditable()) {
            toggleViewTitleEditionMode(true);
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

    public void onViewTitleTextFieldFocusChanged(ObservableValue<? extends Boolean> value, Boolean oldPropertyValue, Boolean newPropertyValue) {
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
            FXUtils.requestFocusDelayed(viewTitleTextField);
        }
    }
}

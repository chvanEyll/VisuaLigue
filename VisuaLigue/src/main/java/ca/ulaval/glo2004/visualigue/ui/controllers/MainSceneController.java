package ca.ulaval.glo2004.visualigue.ui.controllers;

import ca.ulaval.glo2004.visualigue.utils.FXUtils;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainSceneController implements Initializable {

    public static final String VIEW_NAME = "/views/main.fxml";

    @FXML private Pane logoPane;
    @FXML private Pane contentPane;
    @FXML private Button previousButton;
    @FXML private Label sectionTitleLabel;
    @FXML private Pane sectionTitleSpacer;
    @FXML private MainMenuController mainMenuController;
    private ViewFlow viewFlow = new ViewFlow();
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenuController.init(logoPane);
        mainMenuController.onMenuClicked.addHandler(this::onMainMenuClickedHandler);
    }

    public void setStage(Stage stage) {
        this.mainStage = stage;
    }

    private void onMainMenuClickedHandler(Object sender, FXMLLoader requestedView) {
        setMainView(requestedView);
    }

    @FXML
    private void onPreviousButtonClick() {
        previousView();
    }

    private void setMainView(FXMLLoader fxmlLoader) {
        viewFlow.clear();
        viewFlow = new ViewFlow(fxmlLoader);
        setView(fxmlLoader);
    }

    private void onViewChangeRequestedHandler(Object sender, FXMLLoader fxmlLoader) {
        nextView(fxmlLoader);
    }

    private void onViewCloseRequestedHandler(Object sender, Object eventArgs) {
        previousView();
    }

    private void nextView(FXMLLoader view) {
        viewFlow.addView(view);
        setView(view);
    }

    private void previousView() {
        if (viewFlow.count() > 1) {
            FXMLLoader view = viewFlow.moveToPrevious();
            setView(view);
        }
    }

    private void setView(FXMLLoader view) {
        Controller controller = view.getController();
        controller.onViewChangeRequested.setHandler(this::onViewChangeRequestedHandler);
        controller.onViewCloseRequested.setHandler(this::onViewCloseRequestedHandler);
        controller.onFileSelectionRequested.setHandler(this::onFileSelectRequestHandler);
        contentPane.getChildren().clear();
        contentPane.getChildren().add(view.getRoot());
        sectionTitleLabel.textProperty().bind(controller.getTitle());
        FXUtils.setDisplay(previousButton, viewFlow.count() > 1);
        FXUtils.setDisplay(sectionTitleSpacer, viewFlow.count() <= 1);
    }

    private void onFileSelectRequestHandler(Object sender, FileSelectionEventArgs eventArgs) {
        eventArgs.selectedFile = eventArgs.fileChooser.showOpenDialog(mainStage);
    }
}

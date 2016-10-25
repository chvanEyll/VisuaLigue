package ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.PlayEditorController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class PlayManagementController extends ViewController {

    public static final String VIEW_TITLE = "Jeux";
    public static final String VIEW_NAME = "/views/playmanagement/play-management.fxml";
    @FXML private PlayListController playListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playListController.onPlaySelected.setHandler(this::onPlaySelectedEvent);
        playListController.onViewChangeRequested.forward(onViewChangeRequested);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    private void onPlaySelectedEvent(Object sender, PlayModel playModel) {
        View view = InjectableFXMLLoader.loadView(PlayEditorController.VIEW_NAME);
        PlayEditorController controller = (PlayEditorController) view.getController();
        controller.init(playModel);
        onViewChangeRequested.fire(this, view);
    }
}

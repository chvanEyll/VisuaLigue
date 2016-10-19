package ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement;

import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.PlayEditorController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;

public class PlayManagementController extends Controller {

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

    private void onPlaySelectedEvent(Object sender, PlayListItemModel playListItemModel) {
        View view = InjectableFXMLLoader.loadView(PlayEditorController.VIEW_NAME);
        PlayEditorController controller = (PlayEditorController) view.getController();
        try {
            controller.init(playListItemModel.getUUID());
        } catch (PlayNotFoundException ex) {
            Logger.getLogger(PlayManagementController.class.getName()).log(Level.SEVERE, null, ex);
        }
        onViewChangeRequested.fire(this, view);
    }
}

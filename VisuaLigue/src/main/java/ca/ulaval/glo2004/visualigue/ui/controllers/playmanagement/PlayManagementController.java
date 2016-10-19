package ca.ulaval.glo2004.visualigue.ui.controllers.playmanagement;

import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.models.PlayListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PlayManagementController extends Controller {

    public static final String VIEW_TITLE = "Plays";
    public static final String VIEW_NAME = "/views/playmanagement/play-management.fxml";
    @FXML private PlayListController playListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        playListController.onPlaySelected.setHandler(this::onPlaySelectedEvent);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    private void onPlaySelectedEvent(Object sender, PlayListItemModel playListItemModel) {
//        View view = InjectableFXMLLoader.loadView(PlayCreationController.VIEW_NAME);
//        PlayCreationController controller = (PlayCreationController) view.getController();
//        try {
//            controller.init(playListItemModel.getUUID());
//        } catch (PlayNotFoundException ex) {
//            Logger.getLogger(PlayManagementController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        onViewChangeRequested.fire(this, view);
    }

    @FXML
    public void onNewPlayButtonClicked(MouseEvent e) {
//        View view = InjectableFXMLLoader.loadView(PlayCreationController.VIEW_NAME);
//        PlayCreationController controller = (PlayCreationController) view.getController();
//        controller.init();
//        onViewChangeRequested.fire(this, view);
    }
}

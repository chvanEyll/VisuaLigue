package ca.ulaval.glo2004.visualigue.ui.controllers.playcreation;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.PlayEditorController;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement.SportListController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javax.inject.Inject;

public class PlayCreationController extends ControllerBase {

    public static final String VIEW_TITLE = "Création d'un jeu";
    public static final String VIEW_NAME = "/views/playcreation/play-creation.fxml";
    @Inject private PlayService playService;
    @FXML private SportListController sportListController;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sportListController.onSportSelected.setHandler(this::onSportSelectedEvent);
        sportListController.onViewChangeRequested.forward(onViewChangeRequested);
    }

    @Override
    public StringProperty getTitle() {
        return new SimpleStringProperty(VIEW_TITLE);
    }

    private void onSportSelectedEvent(Object sender, SportListItemModel sportListItemModel) {
        try {
            UUID playUUID = playService.createPlay("Nouveau jeu", sportListItemModel.getUUID());
            View view = InjectableFXMLLoader.loadView(PlayEditorController.VIEW_NAME);
            PlayEditorController controller = (PlayEditorController) view.getController();
            controller.init(playUUID);
            onViewChangeRequested.fire(this, view);
        } catch (Exception ex) {
            Logger.getLogger(PlayCreationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

package ca.ulaval.glo2004.visualigue.ui.controllers.playcreation;

import ca.ulaval.glo2004.visualigue.services.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.Controller;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement.SportListController;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javax.inject.Inject;

public class PlayCreationController extends Controller {

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
//        playService.createPlay("Nouveau jeu", sportListItemModel.getUUID());
//
//        View view = InjectableFXMLLoader.loadView(PlayEditorController.VIEW_NAME);
//        PlayEditorController controller = (PlayEditorController) view.getController();
//        try {
//            controller.init(sportListItemModel.getUUID());
//        } catch (SportNotFoundException ex) {
//            Logger.getLogger(PlayCreationController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        onViewChangeRequested.fire(this, view);
    }
}

package ca.ulaval.glo2004.visualigue.ui.controllers.playcreation;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.PlayEditorController;
import ca.ulaval.glo2004.visualigue.ui.controllers.sportmanagement.SportListController;
import ca.ulaval.glo2004.visualigue.ui.converters.PlayModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.SportListItemModel;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.UUID;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javax.inject.Inject;

public class PlayCreationController extends ViewController {

    public static final String VIEW_TITLE = "Création d'un jeu";
    public static final String VIEW_NAME = "/views/playcreation/play-creation.fxml";
    @Inject private PlayService playService;
    @Inject private PlayModelConverter playModelConverter;
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
        UUID playUUID = playService.createPlay("Nouveau jeu", sportListItemModel.getUUID());
        Play play = playService.getPlay(playUUID);
        View view = InjectableFXMLLoader.loadView(PlayEditorController.VIEW_NAME);
        PlayEditorController controller = (PlayEditorController) view.getController();
        controller.init(playModelConverter.convert(play));
        onViewCloseRequested.fire(this, false);
        onViewChangeRequested.fire(this, view);
    }
}

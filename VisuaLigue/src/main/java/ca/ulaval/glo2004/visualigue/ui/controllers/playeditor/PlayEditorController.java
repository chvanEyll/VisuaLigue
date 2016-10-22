package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.itempane.ItemPaneController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import java.util.UUID;
import javafx.fxml.FXML;
import javax.inject.Inject;

public class PlayEditorController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/play-editor.fxml";

    @FXML private ItemPaneController itemPaneController;
    @FXML private SceneController sceneController;
    @Inject private PlayService playService;

    public void init(UUID playUUID) throws PlayNotFoundException {
        Play play = playService.getPlay(playUUID);
        itemPaneController.init(play.getSport().getUUID(), sceneController);
    }

}

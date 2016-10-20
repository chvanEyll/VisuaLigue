package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor;

import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import java.util.UUID;

public class PlayEditorController extends ControllerBase {

    public static final String VIEW_NAME = "/views/playeditor/play-editor.fxml";

    public void init(UUID playUUID) throws PlayNotFoundException {

    }

}

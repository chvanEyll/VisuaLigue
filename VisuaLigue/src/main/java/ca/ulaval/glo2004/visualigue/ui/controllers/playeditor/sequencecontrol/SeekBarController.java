package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.sequencecontrol;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javax.inject.Inject;

public class SeekBarController extends ControllerBase {

    @FXML private GridPane keyframeGridPane;
    @FXML private Button seekBarButton;
    @FXML private Button newFrameButton;
    @Inject private PlayService playService;
    private PlayModel playModel;

    public void init(PlayModel playModel) {
        this.playModel = playModel;
    }

    private void updateKeyframes() {
        Integer playLength = playService.getPlayLength(playModel.getUUID());
        for (Integer index = 0; index < playLength; index++) {

        }
    }

}

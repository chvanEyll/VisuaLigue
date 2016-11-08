package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers;

import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class PlayerPositionModificationController {

    @Inject private PlayService playService;
    private PlayerActorModel playerActorModel;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private PlayModel playModel;
    private FrameModel frameModel;

    public void init(PlayerActorModel playerActorModel, PlayingSurfaceLayerController playingSurfaceLayerController, PlayModel playModel, FrameModel frameModel) {
        this.playerActorModel = playerActorModel;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.playModel = playModel;
        this.frameModel = frameModel;
    }

    @FXML
    protected void onMouseDragged(MouseEvent e) {
        playerActorModel.position.set(playingSurfaceLayerController.getSizeRelativeMousePosition(true));
    }

    @FXML
    protected void onMouseReleased(MouseEvent e) {
        Vector2 position = playingSurfaceLayerController.getSizeRelativeMousePosition(true);
        playService.updatePlayerActorPositionDirect(playModel.getUUID(), frameModel.time.get(), playerActorModel.getUUID(), position);
    }

}

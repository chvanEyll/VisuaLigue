package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.services.play.PlayService;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.converters.ActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;

public class PlayerCreationController {

    public EventHandler onPlayerCreationModeExited = new EventHandler();
    private PlayService playService;
    private ActorModelConverter actorModelConverter;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private LayerController layerController;
    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;
    private ActorModel playerCreationModel;
    private PlayModel playModel;
    private FrameModel frameModel;
    private Boolean enabled = false;

    public PlayerCreationController(PlayingSurfaceLayerController playingSurfaceLayerController, LayerController layerController, ActorModelConverter actorModelConverter, PlayModel playModel, FrameModel frameModel, PlayService playService) {
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.layerController = layerController;
        this.actorModelConverter = actorModelConverter;
        this.playModel = playModel;
        this.frameModel = frameModel;
        this.playService = playService;
        playingSurfaceLayerController.onMouseMoved.addHandler(this::onPlayingSurfaceMouseMoved);
        playingSurfaceLayerController.onMouseClicked.addHandler(this::onPlayingSurfaceMouseClicked);
    }

    public void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        if (!enabled) {
            this.playerCategoryModel = playerCategoryModel;
            this.teamSide = teamSide;
            initActorCreationLayer();
            enabled = true;
        }
    }

    private void initActorCreationLayer() {
        layerController.removeActorLayer(playerCreationModel);
        layerController.setAllOpacity(0.5);
        playerCreationModel = actorModelConverter.convert(playerCategoryModel, teamSide);
        layerController.addActorLayer(playerCreationModel);
        layerController.setAllMouseTransparent(true);
    }

    public void exitPlayerCreationMode() {
        if (enabled) {
            layerController.removeActorLayer(playerCreationModel);
            layerController.setAllMouseTransparent(false);
            layerController.setAllOpacity(1.0);
            enabled = false;
            onPlayerCreationModeExited.fire(this);
        }
    }

    private void onPlayingSurfaceMouseMoved(Object sender, MouseEvent e) {
        if (enabled) {
            playerCreationModel.position.set(playingSurfaceLayerController.getRelativeMousePosition());
        }
    }

    private void onPlayingSurfaceMouseClicked(Object sender, MouseEvent e) {
        if (enabled) {
            Vector2 position = playingSurfaceLayerController.getRelativeMousePosition();
            playService.addPlayer(playModel.getUUID(), frameModel.time.get(), playerCategoryModel.getUUID(), teamSide, 0.0, position);
            initActorCreationLayer();
        }
    }

}

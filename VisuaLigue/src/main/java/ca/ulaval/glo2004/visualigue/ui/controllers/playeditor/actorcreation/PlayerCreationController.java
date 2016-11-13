package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorcreation;

import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.converters.actor.PlayerActorModelConverter;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.scene.input.MouseEvent;
import javax.inject.Inject;

public class PlayerCreationController extends ActorCreationController {

    @Inject private PlayerActorModelConverter playerActorModelConverter;
    private PlayerCategoryModel playerCategoryModel;
    private TeamSide teamSide;

    public void init(PlayerCategoryModel playerCategoryModel, TeamSide teamSide) {
        this.playerCategoryModel = playerCategoryModel;
        this.teamSide = teamSide;
        this.actorModel = playerActorModelConverter.convert(playerCategoryModel, teamSide);
    }

    @Override
    public void onSceneMouseClicked(MouseEvent e) {
        if (enabled) {
            Vector2 worldMousePosition = sceneController.getMouseWorldPosition(true);
            playService.beginUpdate(sceneController.getPlayUUID());
            playService.addPlayerActor(sceneController.getPlayUUID(), sceneController.getTime(), playerCategoryModel.getUUID(), teamSide, 0.0, worldMousePosition);
            playService.endUpdate(sceneController.getPlayUUID());
            initCreationLayer(actorModel);
        }
    }

}

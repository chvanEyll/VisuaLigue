package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;

public interface SceneController {

    void drawFrame(FrameModel frameModel);

    void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide);

    void enterBallCreationMode();

    void enterObstacleCreationMode(ObstacleModel obstacleModel);

    void enterFrameByFrameCreationMode();

    void enterRealTimeCreationMode();

}

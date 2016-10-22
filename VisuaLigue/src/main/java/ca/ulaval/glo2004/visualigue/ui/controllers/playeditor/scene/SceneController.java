package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;

public abstract class SceneController {

    public EventHandler<MousePositionModel> onMousePositionChanged = new EventHandler();
    public EventHandler<Double> onZoomChanged = new EventHandler();
    public EventHandler<Boolean> onPlayerCategoryLabelDisplayEnableChanged = new EventHandler();

    public abstract void drawFrame(FrameModel frameModel);

    public abstract void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide);

    public abstract void enterBallCreationMode(BallModel ballModel);

    public abstract void enterObstacleCreationMode(ObstacleModel obstacleModel);

    public abstract void enterFrameByFrameCreationMode();

    public abstract void enterRealTimeCreationMode();

    public abstract Double getZoom();

    public abstract void setZoom(Double zoom);

    public abstract Double getMinZoom();

    public abstract Double getMaxZoom();

    public abstract void zoomIn();

    public abstract void zoomOut();

    public abstract void autoFit();

    public abstract Boolean isPlayerCategoryLabelDisplayEnabled();

    public abstract void setPlayerCategoryLabelDisplayEnabled(Boolean enabled);

    public abstract void undo();

    public abstract void redo();

}

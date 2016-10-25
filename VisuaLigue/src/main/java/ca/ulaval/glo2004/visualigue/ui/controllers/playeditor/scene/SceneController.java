package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.ViewController;
import ca.ulaval.glo2004.visualigue.ui.models.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;

public abstract class SceneController extends ViewController {

    public static final NavigableSet<Zoom> PREDEFINED_ZOOMS = new TreeSet(Arrays.asList(
            new Zoom(0.5), new Zoom(0.75), new Zoom(1), new Zoom(1.25), new Zoom(1.5), new Zoom(1.75), new Zoom(2), new Zoom(2.5), new Zoom(3), new Zoom(4), new Zoom(5)
    ));

    public EventHandler<MousePositionModel> onMousePositionChanged = new EventHandler();
    public EventHandler<Zoom> onZoomChanged = new EventHandler();
    public EventHandler<Boolean> onPlayerCategoryLabelDisplayEnableChanged = new EventHandler();
    public EventHandler<Object> onPlayerCreationModeExited = new EventHandler();
    public EventHandler<Object> onObstacleCreationModeExited = new EventHandler();
    public EventHandler<Object> onBallCreationModeExited = new EventHandler();
    public EventHandler<Object> onNavigationModeEntered = new EventHandler();
    public EventHandler<Object> onNavigationModeExited = new EventHandler();

    public abstract void init(PlayModel playModel);

    public abstract FrameModel getFrameModel();

    public abstract void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide);

    public abstract void enterBallCreationMode(BallModel ballModel);

    public abstract void enterObstacleCreationMode(ObstacleModel obstacleModel);

    public abstract void enterNavigationMode();

    public abstract void enterFrameByFrameMode();

    public abstract void enterRealTimeMode();

    public abstract Zoom getZoom();

    public abstract void setZoom(Zoom zoom);

    public abstract Zoom getMinZoom();

    public abstract Zoom getMaxZoom();

    public abstract void zoomIn();

    public abstract void zoomOut();

    public abstract void autoFit();

    public abstract Boolean isPlayerCategoryLabelDisplayEnabled();

    public abstract void setPlayerCategoryLabelDisplayEnabled(Boolean enabled);

    public abstract void undo();

    public abstract void redo();

}

package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.models.BallModel;
import ca.ulaval.glo2004.visualigue.ui.models.ObstacleModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class SceneController extends ControllerBase {

    public static final NavigableSet<Zoom> PREDEFINED_ZOOMS = new TreeSet(Arrays.asList(
            new Zoom(0.5), new Zoom(0.75), new Zoom(1), new Zoom(1.25), new Zoom(1.5), new Zoom(1.75), new Zoom(2), new Zoom(2.5), new Zoom(3), new Zoom(4), new Zoom(5)
    ));

    public EventHandler<Vector2> onMousePositionChanged = new EventHandler();
    public EventHandler<Zoom> onZoomChanged = new EventHandler();
    public EventHandler onCreationModeEntered = new EventHandler();
    public EventHandler onPlayerCreationModeExited = new EventHandler();
    public EventHandler onObstacleCreationModeExited = new EventHandler();
    public EventHandler onBallCreationModeExited = new EventHandler();
    public EventHandler onNavigationModeEntered = new EventHandler();
    public EventHandler onNavigationModeExited = new EventHandler();
    public EventHandler onRealTimeCreationModeEntered = new EventHandler();
    public EventHandler onFrameByFrameCreationModeEntered = new EventHandler();

    protected PlayModel playModel;
    protected BooleanProperty showActorLabelsProperty = new SimpleBooleanProperty(false);
    protected BooleanProperty showMovementArrowsProperty = new SimpleBooleanProperty(true);
    protected BooleanProperty resizeActorsOnZoomProperty = new SimpleBooleanProperty(true);
    protected BooleanProperty realTimeModeProperty = new SimpleBooleanProperty(false);

    public abstract void init(PlayModel playModel);

    public abstract void update(Integer time);

    public abstract void enterPlayerCreationMode(PlayerCategoryModel playerCategoryModel, TeamSide teamSide);

    public abstract void enterBallCreationMode(BallModel ballModel);

    public abstract void enterObstacleCreationMode(ObstacleModel obstacleModel);

    public abstract void enterNavigationMode();

    public abstract void enterFrameByFrameMode();

    public abstract void enterRealTimeMode();

    public abstract void toggleRealTimeMode();

    public abstract Zoom getZoom();

    public abstract void setZoom(Zoom zoom);

    public abstract Zoom getMinZoom();

    public abstract Zoom getMaxZoom();

    public abstract void zoomIn();

    public abstract void zoomOut();

    public abstract void autoFit();

    public abstract Boolean isActorLabelDisplayEnabled();

    public abstract void setActorLabelDisplay(Boolean showLabels);

    public abstract Boolean isMovementArrowDisplayEnabled();

    public abstract void setMovementArrowDisplay(Boolean showMovementArrows);

    public abstract Boolean isResizeActorsOnZoomEnabled();

    public abstract void setResizeActorsOnZoom(Boolean resizeActorsOnZoom);

}

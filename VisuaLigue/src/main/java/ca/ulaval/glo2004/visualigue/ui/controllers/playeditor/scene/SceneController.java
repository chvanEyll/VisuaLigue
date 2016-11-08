package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene;

import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.ActorCreationController;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.Arrays;
import java.util.NavigableSet;
import java.util.TreeSet;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javax.inject.Inject;

public abstract class SceneController extends ControllerBase {

    public static final NavigableSet<Zoom> PREDEFINED_ZOOMS = new TreeSet(Arrays.asList(
            new Zoom(0.5), new Zoom(0.75), new Zoom(1), new Zoom(1.25), new Zoom(1.5), new Zoom(1.75), new Zoom(2), new Zoom(2.5), new Zoom(3), new Zoom(4), new Zoom(5)
    ));

    public EventHandler onCreationModeEntered = new EventHandler();
    public EventHandler onCreationModeExited = new EventHandler();
    public EventHandler onNavigationModeEntered = new EventHandler();
    public EventHandler onNavigationModeExited = new EventHandler();
    public EventHandler onRealTimeCreationModeEntered = new EventHandler();
    public EventHandler onFrameByFrameCreationModeEntered = new EventHandler();

    protected PlayModel playModel;
    @Inject public Settings settings;

    public abstract void init(PlayModel playModel);

    public abstract void update(Long time);

    public abstract void enterCreationMode(ActorCreationController actorCreationController);

    public abstract void enterNavigationMode();

    public abstract void enterFrameByFrameMode();

    public abstract void enterRealTimeMode();

    public abstract void toggleRealTimeMode();

    public abstract ObjectProperty<Zoom> zoomProperty();

    public abstract ReadOnlyObjectProperty<Vector2> realWorldMousePositionProperty();

    public abstract Zoom getMinZoom();

    public abstract Zoom getMaxZoom();

    public abstract void zoomIn();

    public abstract void zoomOut();

    public abstract void autoFit();

}

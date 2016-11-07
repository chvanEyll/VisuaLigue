package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers.ActorLayerFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.layout.StackPane;

public class LayerController extends ControllerBase {

    private ActorLayerFactory actorLayerFactory;
    private StackPane layerStackPane;
    private Map<ActorModel, View> actorLayerMap = new HashMap();
    protected PlayingSurfaceLayerController playingSurfaceLayerController;
    protected ObjectProperty<Zoom> zoomProperty;
    protected BooleanProperty showActorLabelsProperty;
    protected BooleanProperty showMovementArrowsProperty;
    protected BooleanProperty resizeActorsOnZoomProperty;

    public LayerController(FrameModel frameModel, ActorLayerFactory actorLayerFactory, StackPane layerStackPane, PlayingSurfaceLayerController playingSurfaceLayerController, ObjectProperty<Zoom> zoomProperty, BooleanProperty showActorLabelsProperty, BooleanProperty showMovementArrowsProperty, BooleanProperty resizeActorsOnZoomProperty) {
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.zoomProperty = zoomProperty;
        this.showActorLabelsProperty = showActorLabelsProperty;
        this.showMovementArrowsProperty = showMovementArrowsProperty;
        this.resizeActorsOnZoomProperty = resizeActorsOnZoomProperty;
        this.actorLayerFactory = actorLayerFactory;
        this.layerStackPane = layerStackPane;
        frameModel.actorModels.addListener(this::onActorStateMapChanged);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        if (change.wasAdded()) {
            addActorLayer((ActorModel) change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorLayer((ActorModel) change.getValueRemoved());
        }
    }

    public void addActorLayer(ActorModel actorModel) {
        View view = actorLayerFactory.create(actorModel);
        ActorLayerController controller = (ActorLayerController) view.getController();
        controller.init(actorModel, playingSurfaceLayerController, zoomProperty, showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
        super.addChild(controller);
        actorLayerMap.put(actorModel, view);
        addLayer(view);
    }

    public void addLayer(View view) {
        layerStackPane.getChildren().add(view.getRoot());
    }

    public void removeActorLayer(ActorModel actorModel) {
        if (actorLayerMap.containsKey(actorModel)) {
            View view = actorLayerMap.get(actorModel);
            actorLayerMap.remove(actorModel);
            removeLayer(view);
        }
    }

    public void removeLayer(View view) {
        ControllerBase controller = (ControllerBase) view.getController();
        controller.clean();
        layerStackPane.getChildren().remove(view.getRoot());
    }

    public void setLayerOpacity(ActorModel actorModel, Double opacity) {
        actorLayerMap.get(actorModel).getRoot().setOpacity(opacity);
    }

    public void setLayerMouseTransparent(ActorModel actorModel, Boolean mouseTransparent) {
        actorLayerMap.get(actorModel).getRoot().setMouseTransparent(mouseTransparent);
    }
}

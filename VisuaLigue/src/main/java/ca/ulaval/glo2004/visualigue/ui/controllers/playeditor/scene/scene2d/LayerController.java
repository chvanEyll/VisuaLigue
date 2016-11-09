package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actorlayers.ActorLayerFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Settings;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.layers.ActorLayerModel;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.layout.StackPane;

public class LayerController extends ControllerBase {

    private ActorLayerFactory actorLayerFactory;
    private StackPane layerStackPane;
    private Map<ActorLayerModel, View> actorLayerMap = new HashMap();
    private PlayModel playModel;
    private FrameModel frameModel;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private ObjectProperty<Zoom> zoomProperty;
    private Settings settings;

    public LayerController(StackPane layerStackPane, ActorLayerFactory actorLayerFactory, PlayModel playModel, FrameModel frameModel, PlayingSurfaceLayerController playingSurfaceLayerController, ObjectProperty<Zoom> zoomProperty, Settings settings) {
        this.layerStackPane = layerStackPane;
        this.actorLayerFactory = actorLayerFactory;
        this.playModel = playModel;
        this.frameModel = frameModel;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.zoomProperty = zoomProperty;
        this.settings = settings;
        frameModel.layerModels.addListener(this::onActorStateMapChanged);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        if (change.wasAdded()) {
            addActorLayer((ActorLayerModel) change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorLayer((ActorLayerModel) change.getValueRemoved());
        }
    }

    public void addActorLayer(ActorLayerModel layerModel) {
        View view = actorLayerFactory.create(layerModel);
        view.getRoot().mouseTransparentProperty().bind(frameModel.isLocked);
        view.getRoot().opacityProperty().bind(frameModel.opacity);
        ActorLayerController controller = (ActorLayerController) view.getController();
        controller.init(layerModel, playModel, playingSurfaceLayerController, zoomProperty, settings);
        super.addChild(controller);
        actorLayerMap.put(layerModel, view);
        addLayer(view);
    }

    public void addLayer(View view) {
        layerStackPane.getChildren().add(view.getRoot());
    }

    public void removeActorLayer(ActorLayerModel layerModel) {
        if (actorLayerMap.containsKey(layerModel)) {
            View view = actorLayerMap.get(layerModel);
            actorLayerMap.remove(layerModel);
            removeLayer(view);
        }
    }

    public void removeLayer(View view) {
        ControllerBase controller = (ControllerBase) view.getController();
        controller.clean();
        layerStackPane.getChildren().remove(view.getRoot());
    }

    public void setLayerOpacity(ActorLayerModel layerModel, Double opacity) {
        actorLayerMap.get(layerModel).getRoot().setOpacity(opacity);
    }

    public void setLayerMouseTransparent(ActorLayerModel layerModel, Boolean mouseTransparent) {
        actorLayerMap.get(layerModel).getRoot().setMouseTransparent(mouseTransparent);
    }
}

package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerViewFactory;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.MapChangeListener;
import javafx.scene.layout.StackPane;

public class LayerController extends ControllerBase {

    private ActorLayerViewFactory actorLayerViewFactory;
    private StackPane layerStackPane;
    private Map<ActorModel, View> actorLayerMap = new HashMap();

    public LayerController(FrameModel frameModel, ActorLayerViewFactory actorLayerViewFactory, StackPane layerStackPane) {
        this.actorLayerViewFactory = actorLayerViewFactory;
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
        View view = actorLayerViewFactory.create(actorModel);
        ActorLayerController controller = (ActorLayerController) view.getController();
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

    public void setAllMouseTransparent(Boolean mouseTransparent) {
        actorLayerMap.values().forEach(view -> view.getRoot().setMouseTransparent(mouseTransparent));
    }

    public void setAllOpacity(Double opacity) {
        actorLayerMap.values().forEach(view -> view.getRoot().setOpacity(opacity));
    }
}

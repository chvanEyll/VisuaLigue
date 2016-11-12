package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor.ActorFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Settings;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.ObjectProperty;
import javafx.collections.MapChangeListener;
import javafx.scene.layout.StackPane;

public class LayerController extends ControllerBase {

    private ActorFactory actorFactory;
    private StackPane layerStackPane;
    private Map<ActorModel, View> layerViews = new HashMap();
    private List<Integer> layerOrder = new ArrayList();
    private PlayModel playModel;
    private FrameModel frameModel;
    private PlayingSurfaceLayerController playingSurfaceLayerController;
    private ObjectProperty<Zoom> zoomProperty;
    private Settings settings;

    public LayerController(StackPane layerStackPane, ActorFactory actorFactory, PlayModel playModel, FrameModel frameModel, PlayingSurfaceLayerController playingSurfaceLayerController, ObjectProperty<Zoom> zoomProperty, Settings settings) {
        this.layerStackPane = layerStackPane;
        this.actorFactory = actorFactory;
        this.playModel = playModel;
        this.frameModel = frameModel;
        this.playingSurfaceLayerController = playingSurfaceLayerController;
        this.zoomProperty = zoomProperty;
        this.settings = settings;
        frameModel.layerModels.addListener(this::onActorStateMapChanged);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        if (change.wasAdded()) {
            addActorModel((ActorModel) change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorModel((ActorModel) change.getValueRemoved());
        }
    }

    public void addActorModel(ActorModel layerModel) {
        View view = actorFactory.create(layerModel);
        ActorController controller = (ActorController) view.getController();
        controller.init(layerModel, playModel, frameModel, playingSurfaceLayerController, zoomProperty, settings);
        super.addChild(controller);
        layerViews.put(layerModel, view);
        addView(view, layerModel.zOrder.get());
    }

    public void addView(View view, Integer zOrder) {
        Integer higherIndex = ListUtils.higherIndex(layerOrder, zOrder);
        if (higherIndex != null) {
            layerStackPane.getChildren().add(higherIndex, view.getRoot());
            layerOrder.add(higherIndex, zOrder);
        } else {
            layerStackPane.getChildren().add(view.getRoot());
            layerOrder.add(zOrder);
        }
    }

    public void removeActorModel(ActorModel layerModel) {
        if (layerViews.containsKey(layerModel)) {
            View view = layerViews.get(layerModel);
            layerViews.remove(layerModel);
            removeView(view);
        }
    }

    public void removeView(View view) {
        ControllerBase controller = (ControllerBase) view.getController();
        controller.clean();
        Integer index = layerStackPane.getChildren().indexOf(view.getRoot());
        layerOrder.remove((int) index);
        layerStackPane.getChildren().remove(view.getRoot());
    }
}

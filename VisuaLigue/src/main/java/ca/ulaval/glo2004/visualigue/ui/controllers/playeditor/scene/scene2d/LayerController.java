package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.actor.ActorFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.SceneController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.utils.ListUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

public class LayerController extends ControllerBase {

    private ActorFactory actorFactory;
    private StackPane layerStackPane;
    private Map<Pair<String, Integer>, ActorModel> actors = new HashMap();
    private Map<ActorModel, View> layerViews = new HashMap();
    private List<Integer> layerOrder = new ArrayList();
    private SceneController sceneController;

    public LayerController(StackPane layerStackPane, ObservableMap<Integer, ActorModel> actorModels, ActorFactory actorFactory, SceneController sceneController) {
        this.layerStackPane = layerStackPane;
        this.actorFactory = actorFactory;
        this.sceneController = sceneController;
        actorModels.addListener(this::onActorStateMapChanged);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        if (change.wasAdded()) {
            addActorModel((ActorModel) change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorModel((ActorModel) change.getValueRemoved());
        }
    }

    public void addActorModel(ActorModel actorModel) {
        View view = actorFactory.create(actorModel);
        ActorController controller = (ActorController) view.getController();
        controller.init(actorModel, sceneController);
        super.addChild(controller);
        layerViews.put(actorModel, view);
        actors.put(new Pair(actorModel.getUUID(), actorModel.instanceID.get()), actorModel);
        addView(view, actorModel.zOrder.get());
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

    public void removeActorModel(ActorModel actorModel) {
        if (layerViews.containsKey(actorModel)) {
            View view = layerViews.get(actorModel);
            actors.remove((new Pair(actorModel.getUUID(), actorModel.instanceID.get())));
            layerViews.remove(actorModel);
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

    public ActorModel findActor(String actorUUID, Integer instanceID) {
        return actors.get(new Pair(actorUUID, instanceID));
    }
}

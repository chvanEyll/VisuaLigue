package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d;

import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.ControllerBase;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerController;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.ActorLayerViewFactory;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers.PlayingSurfaceLayerController;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.layout.StackPane;

public class LayerController extends ControllerBase {

    private ActorLayerViewFactory actorLayerViewFactory;
    private StackPane layerStackPane;
    private NavigationController navigationController;
    private BooleanProperty showActorLabelsProperty;
    private BooleanProperty showMovementArrowsProperty;
    private BooleanProperty resizeActorsOnZoomProperty;
    private View playingSurfaceLayerView;
    private Map<ActorModel, View> actorLayerMap = new HashMap();

    public LayerController(ObservableMap<String, ActorModel> actorModels, ActorLayerViewFactory actorLayerViewFactory, StackPane layerStackPane, NavigationController navigationController, View playingSurfaceLayerView,
            BooleanProperty showActorLabelsProperty, BooleanProperty showMovementArrowsProperty, BooleanProperty resizeActorsOnZoomProperty) {
        this.actorLayerViewFactory = actorLayerViewFactory;
        this.layerStackPane = layerStackPane;
        this.navigationController = navigationController;
        this.playingSurfaceLayerView = playingSurfaceLayerView;
        this.showActorLabelsProperty = showActorLabelsProperty;
        this.showMovementArrowsProperty = showMovementArrowsProperty;
        this.resizeActorsOnZoomProperty = resizeActorsOnZoomProperty;
        addLayer(playingSurfaceLayerView);
        actorModels.addListener(this::onActorStateMapChanged);
    }

    private void onActorStateMapChanged(MapChangeListener.Change change) {
        onActorStateChanged(change);
    }

    private void onActorStateChanged(MapChangeListener.Change<String, ActorModel> change) {
        if (change.wasAdded()) {
            addActorLayer(change.getValueAdded());
        }
        if (change.wasRemoved()) {
            removeActorLayer(change.getValueRemoved());
        }
    }

    public void addActorLayer(ActorModel actorModel) {
        View view = actorLayerViewFactory.create(actorModel, (PlayingSurfaceLayerController) playingSurfaceLayerView.getController(), navigationController.getZoomProperty(), showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
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

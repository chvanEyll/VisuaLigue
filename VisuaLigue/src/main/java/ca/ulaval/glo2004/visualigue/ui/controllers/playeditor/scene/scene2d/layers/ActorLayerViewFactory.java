package ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.scene2d.layers;

import ca.ulaval.glo2004.visualigue.ui.InjectableFXMLLoader;
import ca.ulaval.glo2004.visualigue.ui.View;
import ca.ulaval.glo2004.visualigue.ui.controllers.playeditor.scene.Zoom;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.BallActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.ObstacleActorModel;
import ca.ulaval.glo2004.visualigue.ui.models.actors.PlayerActorModel;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;

public class ActorLayerViewFactory {

    private PlayingSurfaceLayerController playingSurfaceController;
    private ObjectProperty<Zoom> zoomProperty;
    private BooleanProperty showActorLabelsProperty;
    private BooleanProperty showMovementArrowsProperty;
    private BooleanProperty resizeActorsOnZoomProperty;

    public void setPlayingSurfaceController(PlayingSurfaceLayerController playingSurfaceController) {
        this.playingSurfaceController = playingSurfaceController;
    }

    public void setZoomProperty(ObjectProperty<Zoom> zoomProperty) {
        this.zoomProperty = zoomProperty;
    }

    public void setShowActorLabelsProperty(BooleanProperty showActorLabelsProperty) {
        this.showActorLabelsProperty = showActorLabelsProperty;
    }

    public void setShowMovementArrowsProperty(BooleanProperty showMovementArrowsProperty) {
        this.showMovementArrowsProperty = showMovementArrowsProperty;
    }

    public void setResizeActorsOnZoomProperty(BooleanProperty resizeActorsOnZoomProperty) {
        this.resizeActorsOnZoomProperty = resizeActorsOnZoomProperty;
    }

    public View create(ActorModel actorModel) {
        View view;
        if (actorModel instanceof PlayerActorModel) {
            view = InjectableFXMLLoader.loadView(PlayerLayerController.VIEW_NAME);
            PlayerLayerController controller = (PlayerLayerController) view.getController();
            controller.init((PlayerActorModel) actorModel, playingSurfaceController, zoomProperty, showActorLabelsProperty, showMovementArrowsProperty, resizeActorsOnZoomProperty);
        } else if (actorModel instanceof ObstacleActorModel) {
            view = InjectableFXMLLoader.loadView(ObstacleLayerController.VIEW_NAME);
            ObstacleLayerController controller = (ObstacleLayerController) view.getController();
            controller.init((ObstacleActorModel) actorModel, playingSurfaceController, zoomProperty, resizeActorsOnZoomProperty);
        } else if (actorModel instanceof BallActorModel) {
            view = InjectableFXMLLoader.loadView(BallLayerController.VIEW_NAME);
            BallLayerController controller = (BallLayerController) view.getController();
            controller.init((BallActorModel) actorModel, playingSurfaceController, zoomProperty, resizeActorsOnZoomProperty);
        } else {
            throw new RuntimeException("Unsupported ActorModel subclass.");
        }
        return view;
    }

}

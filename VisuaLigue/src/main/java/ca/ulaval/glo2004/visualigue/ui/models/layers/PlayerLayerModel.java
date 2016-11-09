package ca.ulaval.glo2004.visualigue.ui.models.layers;

import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerLayerModel extends ActorLayerModel implements Cloneable {

    public ObjectProperty<Vector2> nextPosition = new SimpleObjectProperty();
    public DoubleProperty orientation = new SimpleDoubleProperty();
    public ObjectProperty<Color> color = new SimpleObjectProperty();
    public StringProperty svgImagePathName = new SimpleStringProperty();
    public StringProperty label = new SimpleStringProperty();

    @Override
    public PlayerLayerModel clone() {
        PlayerLayerModel clonedActor = new PlayerLayerModel();
        clonedActor.position.set(this.position.get().clone());
        clonedActor.hoverText.set(this.hoverText.get());
        clonedActor.nextPosition.set(this.nextPosition.get().clone());
        clonedActor.orientation.set(this.orientation.get());
        clonedActor.color.set(this.color.get());
        clonedActor.svgImagePathName.set(this.svgImagePathName.get());
        clonedActor.label.set(this.label.get());
        return clonedActor;
    }

}

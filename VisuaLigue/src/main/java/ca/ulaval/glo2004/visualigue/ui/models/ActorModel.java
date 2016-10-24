package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class ActorModel extends ModelBase {

    public enum Type {
        PLAYER,
        BALL,
        OBSTACLE;
    }

    public DoubleProperty x = new SimpleDoubleProperty();
    public DoubleProperty y = new SimpleDoubleProperty();
    public DoubleProperty orientation = new SimpleDoubleProperty();
    public ObjectProperty<ActorModel> owner = new SimpleObjectProperty();
    public ObjectProperty<Type> type = new SimpleObjectProperty();
    public ObjectProperty<Color> color = new SimpleObjectProperty();
    public StringProperty builtInImagePathName = new SimpleStringProperty();
    public StringProperty imagePathName = new SimpleStringProperty();
    public StringProperty svgImagePathName = new SimpleStringProperty();
    public StringProperty label = new SimpleStringProperty();
    public StringProperty hoverText = new SimpleStringProperty();
}

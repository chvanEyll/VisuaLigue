package ca.ulaval.glo2004.visualigue.ui.models.actors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BallActorModel extends ActorModel {

    public StringProperty builtInImagePathName = new SimpleStringProperty();
    public StringProperty imagePathName = new SimpleStringProperty();
    public StringProperty playerOwnerUUID = new SimpleStringProperty();
}

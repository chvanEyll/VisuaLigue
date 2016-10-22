package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class BallModel extends ModelBase {

    public StringProperty name = new SimpleStringProperty();
    public StringProperty imagePathName = new SimpleStringProperty();
    public StringProperty builtInImagePathName = new SimpleStringProperty();

}

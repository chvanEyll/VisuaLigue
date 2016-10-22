package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObstacleModel extends Model {

    public StringProperty name = new SimpleStringProperty("Nouvel obstacle");
    public StringProperty builtInImagePathName = new SimpleStringProperty("/images/built-in-obstacle-icons/cone-icon.png");
    public StringProperty newImagePathName = new SimpleStringProperty();
    public StringProperty currentImagePathName = new SimpleStringProperty();
}

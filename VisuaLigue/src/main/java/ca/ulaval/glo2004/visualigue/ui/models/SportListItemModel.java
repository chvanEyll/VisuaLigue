package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel extends Model {

    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty builtInIconFileName = new SimpleStringProperty("/images/generic-sport-icon");

    public SportListItemModel(String name) {
        this.name.set(name);
    }
}

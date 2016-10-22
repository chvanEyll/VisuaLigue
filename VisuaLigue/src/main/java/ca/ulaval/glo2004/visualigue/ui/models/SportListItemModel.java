package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel extends ModelBase {

    public StringProperty name = new SimpleStringProperty();
    public StringProperty builtInIconPathName = new SimpleStringProperty();

    public SportListItemModel(String name) {
        this.name.set(name);
    }
}

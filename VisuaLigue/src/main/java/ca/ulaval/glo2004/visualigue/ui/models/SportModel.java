package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportModel {

    public StringProperty name = new SimpleStringProperty();
    public StringProperty builtInIconFileName = new SimpleStringProperty();

    public SportModel(String name, String builtInIconFileName) {
        this.name.set(name);
        this.builtInIconFileName.set(builtInIconFileName);
    }
}

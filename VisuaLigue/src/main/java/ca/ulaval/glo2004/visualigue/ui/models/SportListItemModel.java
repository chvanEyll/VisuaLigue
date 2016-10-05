package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.Sport;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel {

    public Sport associatedEntity;
    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty builtInIconFileName = new SimpleStringProperty("/images/generic-sport-icon");

    public SportListItemModel(Sport associatedEntity, String name) {
        this.associatedEntity = associatedEntity;
        this.name.set(name);
    }
}

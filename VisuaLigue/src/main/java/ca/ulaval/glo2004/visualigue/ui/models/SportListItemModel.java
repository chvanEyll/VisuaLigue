package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel {

    public Sport associatedSport;
    public StringProperty name = new SimpleStringProperty("Nouveau sport");
    public StringProperty builtInIconFileName = new SimpleStringProperty("/images/generic-sport-icon");

    public SportListItemModel(Sport associatedSport, String name) {
        this.associatedSport = associatedSport;
        this.name.set(name);
    }
}

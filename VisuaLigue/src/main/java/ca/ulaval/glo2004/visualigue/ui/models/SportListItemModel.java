package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel extends ModelBase {

    public StringProperty name = new SimpleStringProperty();
    public StringProperty customIconPathName = new SimpleStringProperty();
    public StringProperty builtInIconPathName = new SimpleStringProperty();

}

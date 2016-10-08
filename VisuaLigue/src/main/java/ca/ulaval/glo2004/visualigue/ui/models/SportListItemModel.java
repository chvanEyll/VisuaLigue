package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SportListItemModel extends Model implements Comparable {

    public StringProperty name = new SimpleStringProperty();
    public StringProperty builtInIconFileName = new SimpleStringProperty();

    public SportListItemModel(String name) {
        this.name.set(name);
    }

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof SportListItemModel)) {
            return 0;
        }
        SportListItemModel model = (SportListItemModel) obj;
        return name.get().compareTo(model.name.get());
    }
}

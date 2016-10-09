package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerCategoryModel extends Model implements Comparable {

    public StringProperty name = new SimpleStringProperty("Nouvelle catégorie");
    public IntegerProperty defaultNumberOfPlayers = new SimpleIntegerProperty(0);
    public ObjectProperty<Color> allyPlayerColor = new SimpleObjectProperty<>(Color.web("#001A80"));
    public ObjectProperty<Color> opponentPlayerColor = new SimpleObjectProperty<>(Color.web("#990000"));

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof PlayerCategoryModel)) {
            return 0;
        }
        PlayerCategoryModel model = (PlayerCategoryModel) obj;
        return name.get().compareTo(model.name.get());
    }
}

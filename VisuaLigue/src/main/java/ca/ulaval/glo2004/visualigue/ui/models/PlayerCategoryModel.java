package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerCategoryModel extends ModelBase implements Comparable<PlayerCategoryModel> {

    public StringProperty name = new SimpleStringProperty("Nouvelle catégorie");
    public StringProperty abbreviation = new SimpleStringProperty("NC");
    public IntegerProperty defaultNumberOfPlayers = new SimpleIntegerProperty(0);
    public ObjectProperty<Color> allyPlayerColor = new SimpleObjectProperty<>(Color.web("#334DB3"));
    public ObjectProperty<Color> opponentPlayerColor = new SimpleObjectProperty<>(Color.web("#990000"));

    @Override
    public int compareTo(PlayerCategoryModel obj) {
        if (obj == this) {
            return 0;
        }
        return name.get().compareTo(obj.name.get());
    }
}

package ca.ulaval.glo2004.visualigue.ui.models;

import javafx.beans.property.*;
import javafx.scene.paint.Color;

public class PlayerCategoryModel extends Model implements Comparable {

    public StringProperty name = new SimpleStringProperty("Nouvelle catégorie");
    public IntegerProperty defaultNumberOfPlayers = new SimpleIntegerProperty(0);
    public ObjectProperty<Color> allyPlayerColor = new SimpleObjectProperty<>(Color.web("#0071BC"));
    public ObjectProperty<Color> opponentPlayerColor = new SimpleObjectProperty<>(Color.web("#C1272D"));

    @Override
    public int compareTo(Object obj) {
        if (!(obj instanceof PlayerCategoryModel)) {
            return 0;
        }
        PlayerCategoryModel model = (PlayerCategoryModel) obj;
        return name.get().compareTo(model.name.get());
    }
}

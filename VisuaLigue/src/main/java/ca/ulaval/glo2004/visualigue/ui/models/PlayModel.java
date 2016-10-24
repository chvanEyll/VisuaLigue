package ca.ulaval.glo2004.visualigue.ui.models;

import java.util.UUID;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PlayModel extends ModelBase {

    public StringProperty title = new SimpleStringProperty();
    public StringProperty defaultThumbnailImagePathName = new SimpleStringProperty("/images/generic-play-thumbnail.png");
    public StringProperty thumbnailImagePathName = new SimpleStringProperty();
    public StringProperty customPlayingSurfaceImagePathName = new SimpleStringProperty();
    public StringProperty builtInPlayingSurfaceImagePathName = new SimpleStringProperty();
    public BallModel ballModel = new BallModel();
    public ObjectProperty<UUID> sportUUID = new SimpleObjectProperty();

    public PlayModel(String title) {
        this.title.set(title);
    }
}

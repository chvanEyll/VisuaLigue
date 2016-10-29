package ca.ulaval.glo2004.visualigue.ui.models;

import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurfaceUnit;
import java.util.UUID;
import javafx.beans.property.*;

public class PlayModel extends ModelBase {

    public StringProperty title = new SimpleStringProperty("Nouveau jeu");
    public StringProperty defaultThumbnailImagePathName = new SimpleStringProperty("/images/generic-play-thumbnail.png");
    public StringProperty thumbnailImagePathName = new SimpleStringProperty();
    public StringProperty customPlayingSurfaceImagePathName = new SimpleStringProperty();
    public StringProperty builtInPlayingSurfaceImagePathName = new SimpleStringProperty();
    public BallModel ballModel = new BallModel();
    public ObjectProperty<UUID> sportUUID = new SimpleObjectProperty();
    public DoubleProperty playingSurfaceWidth = new SimpleDoubleProperty();
    public DoubleProperty playingSurfaceLength = new SimpleDoubleProperty();
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceWidthUnits = new SimpleObjectProperty<>();
    public ObjectProperty<PlayingSurfaceUnit> playingSurfaceLengthUnits = new SimpleObjectProperty<>();

    public PlayModel(String title) {
        this.title.set(title);
    }
}

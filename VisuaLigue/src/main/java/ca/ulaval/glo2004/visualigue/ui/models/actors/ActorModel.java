package ca.ulaval.glo2004.visualigue.ui.models.actors;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.ui.models.ModelBase;
import ca.ulaval.glo2004.visualigue.utils.geometry.Vector2;
import java.util.Objects;
import java.util.UUID;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ActorModel extends ModelBase {

    public IntegerProperty instanceID = new SimpleIntegerProperty(0);
    public IntegerProperty zOrder = new SimpleIntegerProperty();
    public BooleanProperty isLocked = new SimpleBooleanProperty();
    public DoubleProperty opacity = new SimpleDoubleProperty(1.0);
    public BooleanProperty visible = new SimpleBooleanProperty();
    public BooleanProperty showLabel = new SimpleBooleanProperty();
    public ObjectProperty<Vector2> position = new SimpleObjectProperty();
    public StringProperty hoverText = new SimpleStringProperty();

    public ActorModel() {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uuid).append(instanceID).build();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final DomainObject other = (DomainObject) obj;
        return Objects.equals(this.hashCode(), other.hashCode());
    }

}

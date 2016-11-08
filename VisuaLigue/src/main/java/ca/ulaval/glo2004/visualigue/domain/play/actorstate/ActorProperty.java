package ca.ulaval.glo2004.visualigue.domain.play.actorstate;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import java.util.Objects;

public class ActorProperty extends DomainObject {

    private String propertyName;

    public ActorProperty() {
        //Required for JAXB instanciation.
    }

    public ActorProperty(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final ActorProperty other = (ActorProperty) obj;
        return Objects.equals(this.propertyName, other.propertyName);
    }

    @Override
    public int compareTo(DomainObject obj) {
        if (obj == this) {
            return 0;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return 0;
        }
        final ActorProperty other = (ActorProperty) obj;
        return propertyName.compareTo(other.propertyName);
    }

}

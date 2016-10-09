package ca.ulaval.glo2004.visualigue.domain.resource;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import ca.ulaval.glo2004.visualigue.utils.FilenameUtils;

public class LocatedResource extends DomainObject {

    private String name;
    private ResourceLocationType locationType = ResourceLocationType.NONE;

    public LocatedResource() {
        //Required for JAXB instanciation.
    }

    public LocatedResource(String name, ResourceLocationType locationType) {
        this.name = name;
        this.locationType = locationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceLocationType getLocationType() {
        return locationType;
    }

    public void setLocationType(ResourceLocationType locationType) {
        this.locationType = locationType;
    }

    public Boolean isEmpty() {
        return locationType == ResourceLocationType.NONE;
    }

    public Boolean isExternalResource() {
        return locationType == ResourceLocationType.EXTERNAL;
    }

    public String getURIString() {
        if (locationType == ResourceLocationType.EXTERNAL) {
            return FilenameUtils.getURIString(name);
        } else {
            return name;
        }
    }
}

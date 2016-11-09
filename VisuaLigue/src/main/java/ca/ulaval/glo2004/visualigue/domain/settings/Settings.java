package ca.ulaval.glo2004.visualigue.domain.settings;

import ca.ulaval.glo2004.visualigue.domain.DomainObject;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "settings")
public class Settings extends DomainObject {

    private Boolean resizeActorsOnZoom = true;
    private Boolean showMovementArrows = true;
    private Boolean showActorLabels = false;
    private Boolean enableSmoothMovements = true;
    private Boolean showPlayerTrailsOnLastFrame = true;

    public Boolean getResizeActorsOnZoom() {
        return resizeActorsOnZoom;
    }

    public void setResizeActorsOnZoom(Boolean resizeActorsOnZoom) {
        this.resizeActorsOnZoom = resizeActorsOnZoom;
    }

    public Boolean getShowMovementArrows() {
        return showMovementArrows;
    }

    public void setShowMovementArrows(Boolean showMovementArrows) {
        this.showMovementArrows = showMovementArrows;
    }

    public Boolean getShowActorLabels() {
        return showActorLabels;
    }

    public void setShowActorLabels(Boolean showActorLabels) {
        this.showActorLabels = showActorLabels;
    }

    public Boolean getEnableSmoothMovements() {
        return enableSmoothMovements;
    }

    public void setEnableSmoothMovements(Boolean smoothMovements) {
        this.enableSmoothMovements = smoothMovements;
    }

    public Boolean getShowPlayerTrailsOnLastFrame() {
        return showPlayerTrailsOnLastFrame;
    }

    public void setShowPlayerTrailsOnLastFrame(Boolean showPlayerTrailsOnLastFrame) {
        this.showPlayerTrailsOnLastFrame = showPlayerTrailsOnLastFrame;
    }

}

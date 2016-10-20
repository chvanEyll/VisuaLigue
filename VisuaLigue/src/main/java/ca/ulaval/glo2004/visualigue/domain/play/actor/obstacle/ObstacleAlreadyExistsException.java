package ca.ulaval.glo2004.visualigue.domain.play.actor.obstacle;

public class ObstacleAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6163013110930633718L;

    public ObstacleAlreadyExistsException() {
        super();
    }

    public ObstacleAlreadyExistsException(final String message) {
        super(message);
    }
}

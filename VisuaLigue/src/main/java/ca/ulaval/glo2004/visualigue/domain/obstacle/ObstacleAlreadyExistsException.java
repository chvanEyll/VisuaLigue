package ca.ulaval.glo2004.visualigue.domain.obstacle;

public class ObstacleAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6163013110930633718L;

    public ObstacleAlreadyExistsException() {
        super();
    }

    public ObstacleAlreadyExistsException(final String message) {
        super(message);
    }
}

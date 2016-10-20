package ca.ulaval.glo2004.visualigue.domain.obstacle;

public class ObstacleNotFoundException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public ObstacleNotFoundException() {
        super();
    }

    public ObstacleNotFoundException(final String message) {
        super(message);
    }
}

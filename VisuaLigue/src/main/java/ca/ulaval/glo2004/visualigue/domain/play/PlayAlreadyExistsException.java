package ca.ulaval.glo2004.visualigue.domain.play;

public class PlayAlreadyExistsException extends Exception {

    private static final long serialVersionUID = 6163013110930633718L;

    public PlayAlreadyExistsException() {
        super();
    }

    public PlayAlreadyExistsException(final String message) {
        super(message);
    }
}

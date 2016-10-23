package ca.ulaval.glo2004.visualigue.domain.sport;

public class SportAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6163013110930633718L;

    public SportAlreadyExistsException() {
        super();
    }

    public SportAlreadyExistsException(final String message) {
        super(message);
    }
}

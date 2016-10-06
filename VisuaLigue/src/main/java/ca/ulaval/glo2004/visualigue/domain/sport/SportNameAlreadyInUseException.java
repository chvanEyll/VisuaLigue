package ca.ulaval.glo2004.visualigue.domain.sport;

public class SportNameAlreadyInUseException extends Exception {

    public SportNameAlreadyInUseException() {
        super();
    }

    public SportNameAlreadyInUseException(final String message) {
        super(message);
    }

}

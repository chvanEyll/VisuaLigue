package ca.ulaval.glo2004.visualigue.domain.sport;

public class SportNotFoundException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public SportNotFoundException() {
        super();
    }

    public SportNotFoundException(final String message) {
        super(message);
    }
}

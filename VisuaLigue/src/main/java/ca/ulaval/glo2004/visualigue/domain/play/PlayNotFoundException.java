package ca.ulaval.glo2004.visualigue.domain.play;

public class PlayNotFoundException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public PlayNotFoundException() {
        super();
    }

    public PlayNotFoundException(final String message) {
        super(message);
    }
}

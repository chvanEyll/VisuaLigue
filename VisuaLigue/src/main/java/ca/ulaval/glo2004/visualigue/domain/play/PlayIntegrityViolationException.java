package ca.ulaval.glo2004.visualigue.domain.play;

public class PlayIntegrityViolationException extends RuntimeException {

    private static final long serialVersionUID = 751474011098753048L;
    private Play play;

    public PlayIntegrityViolationException() {
        super();
    }

    public PlayIntegrityViolationException(final String message, final Play play) {
        super(message);
        this.play = play;
    }

    public Play getPlay() {
        return play;
    }
}

package ca.ulaval.glo2004.visualigue.domain.image;

public class ImageLoadException extends Exception {

    private static final long serialVersionUID = 751474011098753048L;

    public ImageLoadException() {
        super();
    }

    public ImageLoadException(final String message) {
        super(message);
    }

    public ImageLoadException(final String message, final Exception ex) {
        super(message, ex);
    }
}

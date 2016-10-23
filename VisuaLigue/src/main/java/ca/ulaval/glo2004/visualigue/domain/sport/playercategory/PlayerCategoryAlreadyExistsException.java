package ca.ulaval.glo2004.visualigue.domain.sport.playercategory;

public class PlayerCategoryAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6163013110930633718L;

    public PlayerCategoryAlreadyExistsException() {
        super();
    }

    public PlayerCategoryAlreadyExistsException(final String message) {
        super(message);
    }
}

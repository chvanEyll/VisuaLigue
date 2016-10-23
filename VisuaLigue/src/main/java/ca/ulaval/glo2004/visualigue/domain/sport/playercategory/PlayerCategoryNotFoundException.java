package ca.ulaval.glo2004.visualigue.domain.sport.playercategory;

public class PlayerCategoryNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 751474011098753048L;

    public PlayerCategoryNotFoundException() {
        super();
    }

    public PlayerCategoryNotFoundException(final String message) {
        super(message);
    }
}

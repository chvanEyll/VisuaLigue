package ca.ulaval.glo2004.visualigue.domain.settings;

public class SettingsNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 751474011098753048L;

    public SettingsNotFoundException() {
        super();
    }

    public SettingsNotFoundException(final String message) {
        super(message);
    }
}

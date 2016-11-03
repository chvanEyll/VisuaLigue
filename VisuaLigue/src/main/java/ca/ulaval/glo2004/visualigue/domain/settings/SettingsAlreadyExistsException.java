package ca.ulaval.glo2004.visualigue.domain.settings;

public class SettingsAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 6163013110930633718L;

    public SettingsAlreadyExistsException() {
        super();
    }

    public SettingsAlreadyExistsException(final String message) {
        super(message);
    }
}

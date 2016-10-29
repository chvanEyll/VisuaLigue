package ca.ulaval.glo2004.visualigue.ui.controllers;

public class ViewFlowException extends RuntimeException {

    public ViewFlowException() {
        super();
    }

    public ViewFlowException(final String message) {
        super(message);
    }

    public ViewFlowException(final String message, final Exception ex) {
        super(message, ex);
    }
}

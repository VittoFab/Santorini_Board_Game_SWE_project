package it.polimi.ingsw.server.controller;

public class UnableToBuildException extends Exception {

    public UnableToBuildException() {
    }

    public UnableToBuildException(String message) {
        super(message);
    }

    public UnableToBuildException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToBuildException(Throwable cause) {
        super(cause);
    }

    public UnableToBuildException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
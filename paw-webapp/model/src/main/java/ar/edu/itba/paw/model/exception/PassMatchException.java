package ar.edu.itba.paw.model.exception;

public class PassMatchException extends RuntimeException{
    public PassMatchException(String message) {
        super(message);
    }

    public PassMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

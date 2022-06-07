package ar.edu.itba.paw.model.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable thr) {
        super(message, thr);
    }
}

package ar.edu.itba.paw.model.exception;

public class ApplicationNotFoundException extends RuntimeException{
    public ApplicationNotFoundException() {
        super("Application does not exists");
    }

    public ApplicationNotFoundException(String message) {
        super(message);
    }

    public ApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationNotFoundException(Throwable cause) {
        super(cause);
    }
}

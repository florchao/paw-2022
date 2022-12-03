package ar.edu.itba.paw.model.exception;

public class UserFoundException extends RuntimeException{
    public UserFoundException(String message) {
        super(message);
    }
}

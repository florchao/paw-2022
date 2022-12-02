package ar.edu.itba.paw.model.exception;

public class AccessIsDeniedException extends RuntimeException{
    public AccessIsDeniedException(String message){
        super(message);
    }
}

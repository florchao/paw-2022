package ar.edu.itba.paw.model.exception;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String message){
        super(message);
    }
}

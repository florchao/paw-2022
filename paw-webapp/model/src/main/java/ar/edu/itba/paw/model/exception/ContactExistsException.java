package ar.edu.itba.paw.model.exception;

public class ContactExistsException extends RuntimeException{
    public ContactExistsException(String message){
        super(message);
    }
}

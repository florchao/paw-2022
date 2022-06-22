package ar.edu.itba.paw.model.exception;

import java.util.function.Supplier;

public class JobNotFoundException extends Exception {

    public JobNotFoundException(String message) {
        super(message);
    }

}
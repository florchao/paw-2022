package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.UserFoundException;

import java.util.Optional;

public interface EmployerService {

    Employer create(String name, User id, byte[] image);

    Employer getEmployerById(long id) throws UserFoundException;

}

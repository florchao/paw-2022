package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;

import java.util.Optional;


public interface EmployerService {

    Employer create(String name, User id, byte[] image);

    Optional<Employer> getEmployerById(long id);

}

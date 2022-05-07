package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;

import java.util.Optional;


public interface EmployerService {

    Employer create(String name, long id, byte[] image);

    Optional<Employer> getEmployerById(long id);

}

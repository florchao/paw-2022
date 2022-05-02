package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Employer;

import java.util.Optional;

public interface EmployerDao {

    Employer create(String name, long id);

    Optional<Employer> getEmployerById(long id);

}

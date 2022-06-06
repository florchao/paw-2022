package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface EmployerDao {

    Employer create(String name, User id, byte[] image);

    Optional<Employer> getEmployerById(long id);

}

package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Employer;

public interface EmployerDao {

    Employer create(String name, long id, byte[] image);

}

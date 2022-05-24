package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class EmployerJpaDao implements EmployerDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Employer create(String name, long id, byte[] image) {
        return null;
    }

    @Override
    public Optional<Employer> getEmployerById(long id) {
        return Optional.empty();
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class EmployerJpaDao implements EmployerDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Employer create(String name, User id, byte[] image) {
        final Employer employer = new Employer(name, id);
        employer.getId().setImage(image);
        em.persist(employer);
        return employer;
    }

    @Override
    public Optional<Employer> getEmployerById(long id) {
        return Optional.of(em.find(Employer.class, id));
    }
}

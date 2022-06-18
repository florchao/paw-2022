package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Primary
@Repository
public class EmployerJpaDao implements EmployerDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Employer create(String name, User id, byte[] image) {
        final Employer employer = new Employer(name.toLowerCase(), id);
        employer.getId().setImage(image);
        em.persist(employer);
        return employer;
    }

    @Override
    public Optional<Employer> getEmployerById(long id) {
        return Optional.ofNullable(em.find(Employer.class, id));
    }
}

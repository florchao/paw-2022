package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Ratings;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class RaitingJpaDao implements RaitingDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public void udpateRatingsTable(Employee employeeId, Employer employerId, Long rating) {
        final Ratings newRating = new Ratings(employeeId, employerId, rating.intValue());
        em.persist(newRating);
    }

    public boolean hasAlreadyRated(Employee employeeId, Employer employerId) {
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(r) FROM Ratings r WHERE r.employerID=:employer AND r.employeeID=:employee", Long.class);
        filteredQuery.setParameter("employer", employerId);
        filteredQuery.setParameter("employee", employeeId);
        return filteredQuery.getSingleResult() > 0;
    }
}

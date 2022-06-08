package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class ReviewJpaDao implements ReviewDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Review create(Employee employeeId, Employer employerId, String text) {
        final Review review = new Review(employeeId, employerId, text);
        em.persist(review);
        return review;
    }

    @Override
    public List<Review> getAllReviews(Employee employeeId, Employer id, Long page, int pageSize) {
        final TypedQuery<Review> query;
        if(id!=null) {
            query = em.createQuery("select u from Review u where u.employeeId =:userId and u.employerId <>:employerId", Review.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
                query.setParameter("userId", employeeId);
                query.setParameter("employerId", id);
        }else {
            query = em.createQuery("select u from Review u where u.employeeId =:userId", Review.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
            query.setParameter("userId", employeeId);
        }
        return query.getResultList();
    }

    @Override
    public int getPageNumber(Employee employeeId, Employer id, int pageSize) {
        if(id != null) {
            TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(u) FROM Review u WHERE u.employeeId =:employee and u.employerId <>:employer", Long.class);
            filteredQuery.setParameter("employer", id);
            filteredQuery.setParameter("employee", employeeId);
            return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
        }
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(u) FROM Review u WHERE u.employeeId =:employee", Long.class);
        filteredQuery.setParameter("employee", employeeId);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public Optional<Review> getMyReview(Employee employeeId, Employer id) {
        final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeId=:employeeId and u.employerId=:employerId ", Review.class);
        query.setParameter("employeeId", employeeId);
        query.setParameter("employerId", id);
        List<Review> ans = query.getResultList();
        if(!ans.isEmpty())
            return Optional.ofNullable(query.getSingleResult());
        return Optional.empty();
    }

    @Override
    public List<Review> getMyProfileReviews(Employee employeeId) {
        final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeId=:employeeId ", Review.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();
    }
}

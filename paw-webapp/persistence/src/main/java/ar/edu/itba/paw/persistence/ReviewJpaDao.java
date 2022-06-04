package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
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
    public Review create(long employeeId, long employerId, String text) {
        Optional<Employee> employee=  Optional.ofNullable(em.find(Employee.class, employeeId));
        Optional<Employer> employer=  Optional.ofNullable(em.find(Employer.class, employerId));
        if(employee.isPresent() && employer.isPresent()) {
            final Review review = new Review(employee.get(), employer.get(), text);
            em.persist(review);
            return review;
        }
        return null;
    }

    @Override
    public Optional<List<Review>> getAllReviews(long employeeId, Long id, Long page, int pageSize) {
        Optional<Employee> employee=  Optional.ofNullable(em.find(Employee.class, employeeId));
        Optional<Employer> employer=  Optional.ofNullable(em.find(Employer.class, id));
        if(employee.isPresent() && employer.isPresent()) {
            final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeId =:userId and u.employerId <>:employerId", Review.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
            query.setParameter("userId", employee.get());
            query.setParameter("employerId", employer.get());
            return Optional.ofNullable(query.getResultList());
        }
        return Optional.empty();
    }

    @Override
    public int getPageNumber(long employeeId, Long id, int pageSize) {
        Employee employee = em.find(Employee.class, employeeId);
        Employer employer = em.find(Employer.class, id);
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(u) FROM Review u WHERE u.employeeId =:employee and u.employerId <>:employer", Long.class);
        filteredQuery.setParameter("employer", employer);
        filteredQuery.setParameter("employee", employee);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public Optional<Review> getMyReview(long employeeId, long id) {
        Optional<Employee> employee=  Optional.ofNullable(em.find(Employee.class, employeeId));
        Optional<Employer> employer = Optional.ofNullable(em.find(Employer.class, id));
        if(employee.isPresent() && employer.isPresent()) {
            final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeId=:employeeId and u.employerId=:employerId ", Review.class);
            query.setParameter("employeeId", employee.get());
            query.setParameter("employerId", employer.get());
            List<Review> ans = query.getResultList();
            if(!ans.isEmpty())
                return Optional.ofNullable(query.getSingleResult());
        }
        return Optional.empty();
    }

}

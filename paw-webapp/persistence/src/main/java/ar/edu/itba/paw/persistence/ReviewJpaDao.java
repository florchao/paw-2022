package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
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
    public Optional<List<Review>> getAllReviews(Employee employeeId, Long id, Long page, int pageSize) {
        //StringBuilder sqlQuery = new StringBuilder("SELECT reviewid, employeeid, employerid, review, name FROM review NATURAL JOIN employer WHERE employeeid = ");
        //        sqlQuery.append(employeeId);
        //        if(id != null)
        //            sqlQuery.append(" AND employerId <> ").append(id).append(" LIMIT ").append(pageSize).append(" OFFSET ").append(page*pageSize);;
        //        List<Review> query = jdbcTemplate.query(sqlQuery.toString(), new Object[] {}, REVIEW_ROW_MAPPER);
        //        return Optional.of(query);

        final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeID =:userId", Review.class);
        query.setParameter("userId", employeeId);
        return Optional.ofNullable(query.getResultList());
    }

    //TODO UPDATE
    @Override
    public int getPageNumber(Employee employeeId, Long id, int pageSize) {
        return 0;
    }

    @Override
    public Optional<Review> getMyReview(Employee employeeId, long id) {
        final TypedQuery<Review> query = em.createQuery("select u from Review u where u.employeeId=:emmployeeId", Review.class);
        query.setParameter("employeeId", employeeId);
        return Optional.ofNullable(query.getSingleResult());
    }

}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Review;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Review create(Employee employeeId, Employer employerId, String review, Date created);

    List<Review> getAllReviews(Employee employeeId, Employer id, Long page, int pageSize);

    int getPageNumber(Employee employeeId, Employer id, int pageSize);

    List<Review> getMyProfileReviews(Employee employeeId);

    Optional<Review> getMyReview(Employee employeeId, Employer id);
}

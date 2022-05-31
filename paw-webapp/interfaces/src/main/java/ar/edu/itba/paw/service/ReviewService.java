package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.Optional;


public interface ReviewService {
    Review create(long employeeId, long employerId, String review);
    Optional<List<Review>> getAllReviews(Employee employeeId, Long id, Long page, int pageSize);

    int getPageNumber(Employee employeeId, Long id, int pageSize);

    Optional<Review> getMyReview(Employee employeeId, long id);
}

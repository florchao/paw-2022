package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Review;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ReviewService {
    Review create(long employeeId, long employerId, String review, Date created);
    List<Review> getAllReviews(long employeeId, Long id, Long page, int pageSize);

    List<Review> getMyProfileReviews(long employeeId);
    int getPageNumber(long employeeId, Long id, int pageSize);

    Optional<Review> getMyReview(long employeeId, long id);
}

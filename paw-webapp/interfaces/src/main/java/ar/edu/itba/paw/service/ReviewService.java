package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.Optional;


public interface ReviewService {
    Review create(long employeeId, long employerId, String review);
    Optional<List<Review>> getAllReviews(long employeeId, Long id, Long page, int pageSize);

    int getPageNumber(long employeeId, Long id, int pageSize);

    Optional<Review> getMyReview(long employeeId, long id);
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewDao {
    Review create(long employeeId, long employerId, String review);

    List<Review> getAllReviews(long employeeId, Long id, Long page, int pageSize);

    int getPageNumber(long employeeId, Long id, int pageSize);

    List<Review> getMyProfileReviews(long employeeId);
    Optional<Review> getMyReview(long employeeId, long id);
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Review;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface ReviewService {
    Review create(long employeeId, long employerId, String review, Date created, boolean forEmployee);
    List<Review> getAllReviews(long employeeId, Long id, Long page, int pageSize);

    List<Review> getMyProfileReviews(long employeeId);
    int getPageNumber(long employeeId, Long id, int pageSize);

    Optional<Review> getMyReview(long employeeId, long id);

    List<Review> getAllReviewsEmployer(Long employeeId, long employerId, Long page, int pageSize);

    int getPageNumberEmployer(Long employeeId, long id, int pageSize);

    List<Review> getMyProfileReviewsEmployer(long employeeId);

    Optional<Review> getMyReviewEmployer(long employeeId, long id);
}

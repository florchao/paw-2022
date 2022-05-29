package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.persistence.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    ReviewDao reviewDao;

    @Override
    public Review create(long employeeId, long employerId, String review) {
        return reviewDao.create(employeeId, employerId, review);
    }

    @Override
    public Optional<List<Review>> getAllReviews(long employeeId, Long id) {
        return reviewDao.getAllReviews(employeeId, id);
    }

    @Override
    public Optional<Review> getMyReview(long employeeId, long id) {
        return reviewDao.getMyReview(employeeId, id);
    }
}

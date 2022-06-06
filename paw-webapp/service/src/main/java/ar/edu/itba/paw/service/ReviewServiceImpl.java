package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.persistence.ReviewDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService{

    @Autowired
    private ReviewDao reviewDao;

    @Transactional
    @Override
    public Review create(long employeeId, long employerId, String review) {
        return reviewDao.create(employeeId, employerId, review);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Review> getAllReviews(long employeeId, Long id, Long page, int pageSize) {
        return reviewDao.getAllReviews(employeeId, id, page, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public int getPageNumber(long employeeId, Long id, int pageSize) {
        return reviewDao.getPageNumber(employeeId, id, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Review> getMyReview(long employeeId, long id) {
        return reviewDao.getMyReview(employeeId, id);
    }

    @Override
    public List<Review> getMyProfileReviews(long employeeId) {
        return reviewDao.getMyProfileReviews(employeeId);
    }
}

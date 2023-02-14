package ar.edu.itba.paw.webapp.dto.ReviewDto;

import ar.edu.itba.paw.model.Review;

import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ReviewDto {
    private long reviewId;

    private UserReviewDto from;

    private UserReviewDto to;

    private String created;

    private String review;

    public static ReviewDto fromReview(final UriInfo uriInfo, Review review, boolean forEmployee) {
        ReviewDto dto = new ReviewDto();

        if(forEmployee) {
            dto.to = UserReviewDto.fromEmployee(uriInfo, review.getEmployeeId());
            dto.from = UserReviewDto.fromEmployer(uriInfo, review.getEmployerId());
        }
        else {
            dto.to = UserReviewDto.fromEmployer(uriInfo, review.getEmployerId());
            dto.from = UserReviewDto.fromEmployee(uriInfo, review.getEmployeeId());
        }

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dto.created = formatter.format(review.getCreated());

        dto.review = review.getReview();

        dto.reviewId = review.getReviewId();

        return dto;

    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public UserReviewDto getFrom() {
        return from;
    }

    public void setFrom(UserReviewDto from) {
        this.from = from;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public UserReviewDto getTo() {
        return to;
    }

    public void setTo(UserReviewDto to) {
        this.to = to;
    }
}

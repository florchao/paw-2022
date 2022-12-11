package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Review;

import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewDto {
    private long reviewId;

    private EmployerDto employer;

    private EmployeeDto employee;

    private String created;

    private String review;


    public static ReviewDto fromEmployeeReview(final UriInfo uriInfo, final Review review) {
        final ReviewDto dto = new ReviewDto();

        dto.reviewId = review.getReviewId();

        dto.employer = EmployerDto.fromReviews(uriInfo, review.getEmployerId());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dto.created = formatter.format(review.getCreated());

        dto.review = review.getReview();

        return dto;
    }

    public static ReviewDto fromEmployerReview(final UriInfo uriInfo, final Review review) {
        final ReviewDto dto = new ReviewDto();

        dto.reviewId = review.getReviewId();

        dto.employee = EmployeeDto.fromReview(uriInfo, review.getEmployeeId());

        dto.created = DtoUtils.dateToString(review.getCreated());

        dto.review = review.getReview();

        return dto;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public EmployerDto getEmployer() {
        return employer;
    }

    public void setEmployer(EmployerDto employer) {
        this.employer = employer;
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

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }


}

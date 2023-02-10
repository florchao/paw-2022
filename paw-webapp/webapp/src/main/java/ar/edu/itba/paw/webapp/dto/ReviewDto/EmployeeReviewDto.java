package ar.edu.itba.paw.webapp.dto.ReviewDto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeFromReviewDto;
import ar.edu.itba.paw.webapp.dto.EmployerDto.EmployerDto;

import javax.ws.rs.core.UriInfo;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class EmployeeReviewDto {
    private long reviewId;

    private EmployerDto employer;

    private EmployeeFromReviewDto employee;

    private String created;

    private String review;


    public static EmployeeReviewDto fromEmployeeReview(final UriInfo uriInfo, final Review review) {
        final EmployeeReviewDto dto = new EmployeeReviewDto();

        dto.reviewId = review.getReviewId();

        dto.employer = EmployerDto.fromReviews(uriInfo, review.getEmployerId());

        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        dto.created = formatter.format(review.getCreated());

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

    public EmployeeFromReviewDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeFromReviewDto employee) {
        this.employee = employee;
    }


}

package ar.edu.itba.paw.webapp.dto.ReviewDto;

import ar.edu.itba.paw.model.Review;
import ar.edu.itba.paw.webapp.dto.DtoUtils;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeFromReviewDto;

import javax.ws.rs.core.UriInfo;

public class EmployerReviewsDto {

    private Long reviewId;
    private EmployeeFromReviewDto employee;
    private String created;
    private String review;

    public static EmployerReviewsDto fromEmployerReview(final UriInfo uriInfo, final Review review) {
        final EmployerReviewsDto dto = new EmployerReviewsDto();

        dto.reviewId = review.getReviewId();

        dto.employee = EmployeeFromReviewDto.fromReview(uriInfo, review.getEmployeeId());

        dto.created = DtoUtils.dateToString(review.getCreated());

        dto.review = review.getReview();

        return dto;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public void setReviewId(Long reviewId) {
        this.reviewId = reviewId;
    }

    public EmployeeFromReviewDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeFromReviewDto employee) {
        this.employee = employee;
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
}

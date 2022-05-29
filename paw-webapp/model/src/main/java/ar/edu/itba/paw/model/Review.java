package ar.edu.itba.paw.model;

public class Review {
    public long reviewId;
    public long employeeId;
    public long employerId;
    public String review;

    public String employerName;

    public Review(long reviewId, long employeeId, long employerId, String review) {
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
    }

    public Review(long reviewId, long employeeId, long employerId, String review, String employerName) {
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
        this.employerName = employerName;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(long employerId) {
        this.employerId = employerId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }
}

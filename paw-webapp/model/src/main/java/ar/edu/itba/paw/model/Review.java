package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Review")
@Table(name = "review")
@Embeddable
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_reviewid_seq")
    @SequenceGenerator(name = "review_reviewid_seq", sequenceName = "review_reviewid_seq", allocationSize = 1)
    @Column(name = "reviewID", nullable = false)
    private long reviewId;

    @ManyToOne
    @Embedded
    @JoinColumn(name = "employeeId", nullable = false, referencedColumnName = "employeeID")
    private Employee employeeId;

    @ManyToOne
    @Embedded
    @JoinColumn(name = "employerId", nullable = false, referencedColumnName = "employerID")
    private Employer employerId;

    @Column(name = "review", nullable = false)
    private String review;

    public Review(long reviewId, Employee employeeId, Employer employerId, String review) {
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
    }

    public Review(Employee employeeId, Employer employerId, String review) {
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
    }

    public Review() {
    }

    public Employee getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employeeId = employeeId;
    }

    public Employer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Employer employerId) {
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

    public void firstWordsToUpper(Review review) {
        StringBuilder finalName = new StringBuilder();
        for (String word : employerId.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        review.setReview(finalName.toString());
    }
}

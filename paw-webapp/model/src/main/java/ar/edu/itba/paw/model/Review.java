package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity(name = "Review")
@Table(name = "review")
@Embeddable
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_reviewid_seq")
    @SequenceGenerator(name = "review_reviewid_seq", sequenceName = "review_reviewid_seq", allocationSize = 1)
    @Column(name = "reviewID", nullable = false)
    public long reviewId;

    @ManyToOne
    @Embedded
    @JoinColumn(name = "employeeID", nullable = false, referencedColumnName = "employeeID")
    public Employee employeeId;

    @ManyToOne
    @Embedded
    @JoinColumn(name = "employerID", nullable = false)
    public Employer employerId;

    @Column(name = "review", nullable = false)
    public String review;

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

    public String firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : employerId.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        return finalName.toString();
    }
}

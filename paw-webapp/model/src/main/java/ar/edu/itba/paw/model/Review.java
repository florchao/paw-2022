package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employeeId", nullable = false, referencedColumnName = "employeeID")
    private Employee employeeId;

    @ManyToOne
    @Embedded
    @OnDelete(action = OnDeleteAction.CASCADE)
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

}

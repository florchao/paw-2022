package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date created;

    @Column(name = "review", nullable = false)
    private String review;

    public Review(long reviewId, Employee employeeId, Employer employerId, String review, Date created) {
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
        this.created = created;
    }

    public Review(Employee employeeId, Employer employerId, String review, Date created) {
        this.employeeId = employeeId;
        this.employerId = employerId;
        this.review = review;
        this.created = created;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return reviewId == review1.reviewId && Objects.equals(employeeId, review1.employeeId) && Objects.equals(employerId, review1.employerId) && Objects.equals(created, review1.created) && Objects.equals(review, review1.review);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId, employeeId, employerId, created, review);
    }
}

package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "ratings")
@Entity(name = "Ratings")
@Embeddable
public class Ratings implements Serializable {
    @OneToOne
    @JoinColumn(name = "employeeID", referencedColumnName = "employeeID", nullable = false)
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Employee employeeID;
    @OneToOne
    @JoinColumn(name = "employerID",referencedColumnName = "employerID", nullable = false)
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Employer employerID;
    @Column(nullable = false)
    private int rating;

    public Ratings(Employee employee, Employer employer, int rating) {
        this.employeeID = employee;
        this.employerID = employer;
        this.rating = rating;
    }

    public Employee getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employee employeeID) {
        this.employeeID = employeeID;
    }

    public Employer getEmployerID() {
        return employerID;
    }

    public void setEmployerID(Employer employerID) {
        this.employerID = employerID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}

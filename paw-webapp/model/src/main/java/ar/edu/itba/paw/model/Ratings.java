package ar.edu.itba.paw.model;

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
    public Employee employeeID;
    @OneToOne
    @JoinColumn(name = "employerID",referencedColumnName = "employerID", nullable = false)
    @EmbeddedId
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

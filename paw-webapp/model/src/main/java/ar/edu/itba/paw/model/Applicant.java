package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Applicant")
@Table(name = "applicants")
@Embeddable
public class Applicant implements Serializable {
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "jobID", nullable = false)
    private Job jobID;
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employeeID", nullable = false, referencedColumnName = "employeeID")
    private Employee employeeID;

    public Applicant(){
    }

    public Applicant(Job jobID) {
        this.jobID = jobID;
    }

    public Applicant(Job jobID, Employee employeeID) {
        this.jobID = jobID;
        this.employeeID = employeeID;
    }

    public Job getJobID() {
        return jobID;
    }

    public void setJobID(Job jobID) {
        this.jobID = jobID;
    }

    public Employee getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Employee employeeID) {
        this.employeeID = employeeID;
    }
}

package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "Applicant")
@Table(name = "applicants")
@Embeddable
public class Applicant implements Serializable {
    @OneToOne
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "jobID", nullable = false)
    private Job jobID;
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employeeID", nullable = false, referencedColumnName = "employeeID")
    private Employee employeeID;

    @Column(nullable = false)
    private int status;

    public Applicant(){
    }

    public Applicant(Job jobID) {
        this.jobID = jobID;
    }

    public Applicant(Job jobID, Employee employeeID) {
        this.jobID = jobID;
        this.employeeID = employeeID;
        this.status = 0;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void firstWordsToUpper(Employee employee) {
        StringBuilder finalName = new StringBuilder();
        for (String word : employee.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        employee.setName(finalName.toString());
    }
}

package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "contact")
@Entity(name = "Contact")
@Embeddable
public class Contact implements Serializable {

    @OneToOne
    @JoinColumn(name = "employeeID", referencedColumnName = "employeeID", nullable = false)
    @EmbeddedId
    public Employee employeeID;
    @OneToOne
    @JoinColumn(name = "employerID",referencedColumnName = "employerID", nullable = false)
    @EmbeddedId
    private Employer employerID;
    @Column(name = "message", length = 100, nullable = false)
    private String contactMessage;
    @Column(name = "phone", length = 100, nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private Date created;

    public Contact() {
    }

    public Contact(Employee employeeID, Employer employerID, String message, String phoneNumber, Date created) {
        this.employeeID = employeeID;
        this.employerID = employerID;
        this.contactMessage = message;
        this.created = created;
        this.phoneNumber = phoneNumber;
    }

    public Contact(Employee employeeID, String message, String phoneNumber, Date created, Employer employerID) {
        this.employeeID = employeeID;
        this.contactMessage = message;
        this.created = created;
        this.phoneNumber = phoneNumber;
        this.employerID = employerID;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmployeeID(Employee employeeID) {
        this.employeeID = employeeID;
    }

    public Employee getEmployeeID() {
        return employeeID;
    }

    public String getMessage() {
        return contactMessage;
    }

    public Date getCreated() {
        return created;
    }

    public Employer getEmployerID() {
        return employerID;
    }

    public void setEmployerID(Employer employerID) {
        this.employerID = employerID;
    }

    public void setMessage(String message) {
        this.contactMessage = message;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : getEmployerID().getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        getEmployerID().setName(finalName.toString());
    }
}

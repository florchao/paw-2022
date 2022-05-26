package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "contact")
@Entity(name = "Contact")
@Embeddable
public class Contact implements Serializable {

    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employeeID", nullable = false)
    public Employee employeeID;

    //TODO: A Foreign key refering ar.edu.itba.paw.model.Contact from ar.edu.itba.paw.model.Contact has the wrong number of column. should be 2
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employerID", nullable = false)
    public Employer employerID;
    @Column(name = "message", length = 100, nullable = false)
    public String contactMessage;
    @Column(name = "phone", length = 100, nullable = false)
    public String phoneNumber;

    @Column(nullable = false)
    public Date created;

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

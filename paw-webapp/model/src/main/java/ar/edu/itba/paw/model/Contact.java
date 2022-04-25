package ar.edu.itba.paw.model;

import java.util.Date;

public class Contact {
    public long employeeID;
    public long employerID;
    public String email;
    public String employer;
    public String contactMessage;
    public String phoneNumber;
    public Date created;

    public Contact(long employeeID, long employerID, String message, String phoneNumber, Date created) {
        this.employeeID = employeeID;
        this.employerID = employerID;
        this.contactMessage = message;
        this.created = created;
        this.phoneNumber = phoneNumber;
    }

    public Contact(long employeeID, String email, String employer, String message, String phoneNumber, Date created) {
        this.employeeID = employeeID;
        this.email = email;
        this.employer = employer;
        this.contactMessage = message;
        this.created = created;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public long getEmployeeID() {
        return employeeID;
    }

    public long getEmployerID() {
        return employerID;
    }

    public String getMessage() {
        return contactMessage;
    }

    public Date getCreated() {
        return created;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmployerID(int employerID) {
        this.employerID = employerID;
    }

    public void setMessage(String message) {
        this.contactMessage = message;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package ar.edu.itba.paw.model;

import java.util.Date;

public class Contact {
    public long employeeID;
    public long employerID;
    public String contactMessage;
    public Date created;

    public Contact(long employeeID, long employerID, String message, Date created) {
        this.employeeID = employeeID;
        this.employerID = employerID;
        this.contactMessage = message;
        this.created = created;
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
}

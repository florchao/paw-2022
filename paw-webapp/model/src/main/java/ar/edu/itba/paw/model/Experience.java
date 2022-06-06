package ar.edu.itba.paw.model;

import java.sql.Date;
public class Experience {
    private long id;
    private long employeeId;
    private String title;
    private Date since;
    private Date until;
    private String description;

    public Experience(long id, long employeeId, String title, Date since, Date until, String description) {
        this.id = id;
        this.employeeId = employeeId;
        this.title = title;
        this.since = since;
        this.until = until;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return getId()+" - "+getEmployeeId() +" - "+getTitle()+" - "+getSince()+" - "+getUntil()+" - "+getDescription();
    }
}

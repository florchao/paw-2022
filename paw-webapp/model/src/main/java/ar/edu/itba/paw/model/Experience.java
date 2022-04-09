package ar.edu.itba.paw.model;

import java.sql.Time;

public class Experience {
    private long id;
    private long employeeId;
    private String title;
    private Time since;
    private Time until;
    private String description;

    public Experience(long id, long employeeId, String title, Time since, Time until, String description) {
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

    public Time getSince() {
        return since;
    }

    public void setSince(Time since) {
        this.since = since;
    }

    public Time getUntil() {
        return until;
    }

    public void setUntil(Time until) {
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

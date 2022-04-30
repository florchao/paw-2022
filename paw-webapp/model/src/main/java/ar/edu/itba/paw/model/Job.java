package ar.edu.itba.paw.model;

import java.util.List;

public class Job {
    private String title;
    private String location;
    private long jobId;
    private long employerId;
    private String availability;
    private long experienceYears;
    private String abilities;

    public Job(String title, String location, long jobId, long employerId, String availability, long experienceYears, String abilities) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.employerId = employerId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(long employerId) {
        this.employerId = employerId;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setAbilities(String abilities) {
        this.abilities = abilities;
    }
}

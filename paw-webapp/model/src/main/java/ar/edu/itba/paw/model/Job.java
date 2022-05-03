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
    private String description;
    private String employerName;
    private String employerUsername;

    private List<String> availabilityArr;
    private List<String> abilitiesArr;

    public Job(String title, String location, long jobId, long employerId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.employerId = employerId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
    }

    public Job(String title, String location, long jobId, String availability, long experienceYears, String abilities, String description) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
    }

    public Job(String title, String location, long jobId, String availability, long experienceYears, String abilities, String description, String employerName) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.description = description;
        this.employerName = employerName;
    }

    public Job(String title, String location, long jobId, List<String>  availability, long experienceYears, List<String> abilities, String description, String employerName) {
        this.title = title;
        this.location = location;
        this.jobId = jobId;
        this.availabilityArr = availability;
        this.experienceYears = experienceYears;
        this.abilitiesArr = abilities;
        this.description = description;
        this.employerName = employerName;
    }

    public Job(String title, String employerUsername) {
        this.title = title;
        this.employerUsername = employerUsername;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public List<String> getAvailabilityArr() {
        return availabilityArr;
    }

    public void setAvailabilityArr(List<String> availabilityArr) {
        this.availabilityArr = availabilityArr;
    }

    public List<String> getAbilitiesArr() {
        return abilitiesArr;
    }

    public void setAbilitiesArr(List<String> abilitiesArr) {
        this.abilitiesArr = abilitiesArr;
    }
}

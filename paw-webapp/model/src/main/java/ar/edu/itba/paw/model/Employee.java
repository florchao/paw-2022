package ar.edu.itba.paw.model;

public class Employee {
    private String name;
    private String location;
    private long id;
    private String availability;
    private long experienceYears;
    private String abilities;

    public Employee(String name, String location, long id, String availability, long experienceYears, String abilities) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return getId() + " - " + getName() + " - " + getLocation() + " - " + getAvailability();
    }
}

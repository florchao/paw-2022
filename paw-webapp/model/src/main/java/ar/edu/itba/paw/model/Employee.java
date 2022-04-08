package ar.edu.itba.paw.model;

public class Employee {
    private String name;
    private String location;
    private long id;
    private String availability;

    public Employee(String name, String location, long id, String availability) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.availability = availability;
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

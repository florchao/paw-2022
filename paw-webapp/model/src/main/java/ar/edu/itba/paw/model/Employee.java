package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity(name = "Employee")
@Table(name = "employee")
@SecondaryTable(name = "users",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "userId"))
@Embeddable
public class Employee implements Serializable {

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String location;
    @OneToOne
    @EmbeddedId
    @JoinColumn(name = "employeeID", nullable = false)
    private User id;
    @Column(length = 100, nullable = false)
    private String availability;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> availabilityArr;
    @Column(nullable = false)
    private long experienceYears;
    @Column(length = 100, nullable = false)
    private String abilities;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> abilitiesArr;

    public Employee() {
    }

    public Employee(String name, String location, User id, String availability, long experienceYears, String abilities) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
    }
    public Employee(String name, String location, User id, List<String> availabilityArr, long experienceYears, List<String> abilitiesArr) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.availabilityArr = availabilityArr;
        this.experienceYears = experienceYears;
        this.abilitiesArr = abilitiesArr;
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

    public User getId() {
        return id;
    }

    public void setId(User id) {
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

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        setName(finalName.toString());
    }

    @Override
    public String toString() {
        return getId()+ " - "
                + getName() + " - "
                + getLocation() + " - "
                + getAvailabilityArr() + " - "
                + getExperienceYears() + " - "
                + getAbilitiesArr();
    }
}

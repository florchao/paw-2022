package ar.edu.itba.paw.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity(name = "Employee")
@Table(name = "employee")
public class Employee implements Serializable {
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String location;
    @OneToOne
    @EmbeddedId
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "employeeID", nullable = false)
    private User id;
    @Column(length = 100, nullable = false)
    private String availability;
    @Column(nullable = false)
    private long experienceYears;

    @Column(nullable = false)
    private long hourlyFee;
    @Column(length = 100, nullable = false)
    private String abilities;
    @Column(nullable = false)
    private float rating;
    @Column(nullable = false)
    private long voteCount;

    public Employee() {
    }

    public Employee(String name, String location, User id, String availability, long experienceYears, long hourlyFee, String abilities) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.availability = availability;
        this.experienceYears = experienceYears;
        this.abilities = abilities;
        this.hourlyFee = hourlyFee;
    }
    public Employee(String name, String location, User id, long experienceYears) {
        this.name = name;
        this.location = location;
        this.id = id;
        this.experienceYears = experienceYears;
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

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
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

    public long getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(long hourlyFee) {
        this.hourlyFee = hourlyFee;
    }

    public String firstWordsToUpper() {
        StringBuilder finalName = new StringBuilder();
        for (String word : getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        return finalName.toString();
    }


    @Override
    public String toString() {
        return getId()+ " - "
                + getName() + " - "
                + getLocation() + " - "
                + getExperienceYears() + " - ";
    }

    public List<String> nameAbilities(List<String> abilities, String language){
        ArrayList<String> employeeAbilities = new ArrayList<>();
        if(language.startsWith("es"))
            for (String ability: abilities) {
                employeeAbilities.add(Abilities.getAbilityById(Integer.parseInt(ability)).getNameEs());
            }
        else
            for (String ability: abilities) {
                employeeAbilities.add(Abilities.getAbilityById(Integer.parseInt(ability)).getName());
            }
        return employeeAbilities;
    }

    public List<String> nameAvailabilities(List<String> availabilityAr, String language){
        ArrayList<String> employeeAvailabilities = new ArrayList<>();
        if(language.startsWith("es"))
            for (String availability: availabilityAr) {
                employeeAvailabilities.add(Availability.getAvailabilityById(Integer.parseInt(availability)).getNameEs());
            }
        else
            for (String availability: availabilityAr) {
                employeeAvailabilities.add(Availability.getAvailabilityById(Integer.parseInt(availability)).getName());
            }
        return employeeAvailabilities;
    }

    public List<String> getAvailabilityArr(String availability, String language){
        List<String> aux = new ArrayList<>(Arrays.asList(availability.split(",")));
        return nameAvailabilities(aux, language);
    }

    public List<String> getAbilitiesArr(String abilities, String language){
        List<String> aux = new ArrayList<>(Arrays.asList(abilities.split(",")));
        return nameAbilities(aux, language);
    }

    public String nameLocation(String location, String language){
        if(language.startsWith("es"))
            return Location.getLocationById(Integer.parseInt(location)).getNameEs();
        else
            return Location.getLocationById(Integer.parseInt(location)).getName();
    }

    public List<String> getAvailabilityIds(String availability){
        return new ArrayList<>(Arrays.asList(availability.split(",")));
    }

    public List<String> getAbilitiesIds(String abilities){
        return new ArrayList<>(Arrays.asList(abilities.split(",")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return experienceYears == employee.experienceYears && hourlyFee == employee.hourlyFee && Float.compare(employee.rating, rating) == 0 && voteCount == employee.voteCount && Objects.equals(name, employee.name) && Objects.equals(location, employee.location) && Objects.equals(id, employee.id) && Objects.equals(availability, employee.availability) &&  Objects.equals(abilities, employee.abilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, location, id, availability, experienceYears, abilities, rating, voteCount, hourlyFee);
    }

}

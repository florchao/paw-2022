package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesAnnotation;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailability;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.util.List;

public class FilterForm {

    @Pattern(regexp = "[a-zA-z\\s']+|^$")
    @Size(max = 100)
    private String name;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long experienceYears;

    private List<Experience> experiencesList;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long pageNumber;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+|")
    @Size(max = 100)
    private String location;

    @CheckboxesAbilitiesAnnotation
    private String[] abilities;

    @CheckboxesAvailability
    private String[] availability;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(long pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Experience> getExperiencesList() {
        return experiencesList;
    }

    public void setExperiencesList(List<Experience> experiencesList) {
        this.experiencesList = experiencesList;
    }

    public String[] getAbilities() {
        return abilities;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setAbilities(String[] abilities) {
        this.abilities = abilities;
    }

    public String[] getAvailability() {
        return availability;
    }

    public void setAvailability(String[] availability) {
        this.availability = availability;
    }
}

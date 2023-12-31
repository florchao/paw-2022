package ar.edu.itba.paw.webapp.dto.JobDto;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class JobCreateDto {

    @Size(max = 25)
    private String title;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+")
    @Size(max = 100)
    private String location;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long experienceYears;

//    @CheckboxesAbilitiesEdit
    private String[] abilities;

//    @CheckboxesAvailabilityJobAnnotation
    private String availability;

    @Size(max = 4000, min = 10)
    private String description;

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

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String[] getAbilities() {
        return abilities;
    }

    public void setAbilities(String[] abilities) {
        this.abilities = abilities;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

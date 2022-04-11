package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Experience;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.*;
import java.sql.Time;
import java.util.List;

public class EmployeeForm {

    @Pattern(regexp = "[\\w-_.]+@([\\w]+.)+[\\w]{2,4}")
    private String mail;

    @Pattern(regexp = "[a-zA-z\\s]+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat
    private long experienceYears;

    private List<Experience> experiencesList;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+")
    @Size(max = 100)
    private String location;

    @NotNull
    private String abilities;

    @NotNull
    private String availability;

    public String getMail() {
        return mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Experience> getExperiencesList() {
        return experiencesList;
    }

    public void setExperiencesList(List<Experience> experiencesList) {
        this.experiencesList = experiencesList;
    }

    public String getAbilities() {
        return abilities;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public long getExperienceYears() {
        return experienceYears;
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

    private static class ExperiencesForm{

        @Size(min=6, max = 50)
        private String title;

        private Time from;

        private Time until;

        @Size(max = 200)
        private String description;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Time getFrom() {
            return from;
        }

        public void setFrom(Time from) {
            this.from = from;
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
    }
}

package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Experiences;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.List;

public class EmployeeForm {

    @Pattern(regexp = "[\\w-_.]+@([\\w]+.)+[\\w]{2,4}")
    private String mail;

    @Pattern(regexp = "[a-zA-z\\s]+")
    @Size(max = 100)
    private String name;

    private List<Experiences> experiencesList;

    @Pattern(regexp = "[a-zA-z]+")
    @Size(max = 100)
    private String location;

    private String habilities;

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

    public List<Experiences> getExperiencesList() {
        return experiencesList;
    }

    public void setExperiencesList(List<Experiences> experiencesList) {
        this.experiencesList = experiencesList;
    }

    public String getHabilities() {
        return habilities;
    }

    public void setHabilities(String habilities) {
        this.habilities = habilities;
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

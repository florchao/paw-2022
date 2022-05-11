package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAbilitiesEdit;
import ar.edu.itba.paw.webapp.constraint.annotation.CheckboxesAvailabilitiesEdit;
import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.util.List;

public class EmployeeForm {

    @Pattern(regexp = "[a-zA-z\\s']+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long experienceYears;

    private List<Experience> experiencesList;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+")
    @Size(max = 100)
    private String location;

    @CheckboxesAbilitiesEdit
    private String[] abilities;

    @CheckboxesAvailabilitiesEdit
    private String[] availability;

    @NotEmptyFile
    private CommonsMultipartFile image;

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public String fromArrtoString(String[] arr){
        StringBuilder ret = new StringBuilder();
        for (String str: arr) {
            ret.append(str).append(",");
        }
        return ret.substring(0, ret.length() - 2);
    }
}

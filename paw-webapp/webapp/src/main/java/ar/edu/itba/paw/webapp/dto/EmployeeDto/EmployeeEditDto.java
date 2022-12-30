package ar.edu.itba.paw.webapp.dto.EmployeeDto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class EmployeeEditDto {

    @Pattern(regexp = "[a-zA-z\\s']+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long experienceYears;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+")
    @Size(max = 100)
    private String location;

//    @CheckboxesAbilitiesEdit
    private String[] abilities;

//    @CheckboxesAvailabilitiesEdit
    private String[] availability;

    @DecimalMin("0")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long hourlyFee;

    private byte[] image;

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
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

    public String[] getAbilities() {
        return abilities;
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

    public long getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(long hourlyFee) {
        this.hourlyFee = hourlyFee;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}

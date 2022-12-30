package ar.edu.itba.paw.webapp.dto.EmployeeDto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class EmployeeCreateDto {
    @Email(regexp = "[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}")
    @NotBlank
//    @UniqueEmailAnnotation
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @Pattern(regexp = "[a-zA-z\\s'-]+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @DecimalMin("0")
    @DecimalMax("100")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long experienceYears;

    @DecimalMin("0")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long hourlyFee;

    @Pattern(regexp = "[a-z A-z\\s0-9,]+")
    @Size(max = 100)
    private String location;

//    @CheckboxesAbilitiesEdit
    private String[] abilities;

//    @CheckboxesAvailabilitiesEdit
    private String[] availability;

    private byte[] image;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public long getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(long hourlyFee) {
        this.hourlyFee = hourlyFee;
    }
}

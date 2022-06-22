package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotation;
import ar.edu.itba.paw.webapp.constraint.annotation.UniqueEmailAnnotation;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@PasswordMatchesAnnotation
public class EmployerForm {

    @Pattern(regexp = "[a-zA-z\\s'-]+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @Pattern(regexp = "[a-zA-z\\s'-]+|^$")
    @NotBlank
    @Size(max = 100)
    private String lastname;

    @Email(regexp = "[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}")
    @NotBlank
    @UniqueEmailAnnotation
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotEmptyFile
    private CommonsMultipartFile image;

    public CommonsMultipartFile getImage() {
        return image;
    }

    public void setImage(CommonsMultipartFile image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

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
}

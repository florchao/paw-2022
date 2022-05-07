package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.NotEmptyFile;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployerForm {

    @Pattern(regexp = "[a-zA-z\\s']+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @Pattern(regexp = "[a-zA-z\\s']+|^$")
    @NotBlank
    @Size(max = 100)
    private String lastname;

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
}

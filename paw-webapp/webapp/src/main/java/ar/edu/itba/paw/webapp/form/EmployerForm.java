package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

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

    //private String photo;


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

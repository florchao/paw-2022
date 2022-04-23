package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContactForm {

    @Pattern(regexp = "[a-zA-z\\s]+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;

    @Pattern(regexp = "[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
    // source: https://www.w3resource.com/javascript/form/email-validation.php
    private String phone;

    @NotBlank
    private String content;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

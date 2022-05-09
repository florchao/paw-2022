package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;

public class ContactForm {

    @Pattern(regexp = "[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
    // source: https://www.w3resource.com/javascript/form/email-validation.php
    private String phone;

    @NotBlank
    private String content;

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

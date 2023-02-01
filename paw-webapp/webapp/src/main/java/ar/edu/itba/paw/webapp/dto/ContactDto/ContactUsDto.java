package ar.edu.itba.paw.webapp.dto.ContactDto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ContactUsDto {

    @Pattern(regexp = "[a-zA-z\\s]+|^$")
    @NotBlank
    @Size(max = 100)
    private String name;
    @Email
    @NotBlank
    private String mail;
    @NotBlank
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

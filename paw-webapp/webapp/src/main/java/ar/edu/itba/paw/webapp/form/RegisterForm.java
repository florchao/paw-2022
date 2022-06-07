package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.constraint.annotation.PasswordMatchesAnnotation;
import ar.edu.itba.paw.webapp.constraint.annotation.RolesAnnotation;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@PasswordMatchesAnnotation
public class RegisterForm {

    @Email(regexp = "[\\w-+_.]+@([\\w]+.)+[\\w]{1,100}")
    @NotBlank
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @RolesAnnotation
    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
}

package ar.edu.itba.paw.webapp.dto.ContactDto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ContactCreateDto {

    @Pattern(regexp = "[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*")
    private String phone;
    @NotBlank
    private String content;

    @NotNull
    private Long employeeId;

    @NotNull
    private Long employerId;

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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }
}

package ar.edu.itba.paw.webapp.dto.ReviewDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewCreateDto {

    @Size(min = 10, max = 1000)
    private String content;

    @NotNull
    private Long employerId;

    @NotNull
    private Long employeeId;

    @NotNull
    private Boolean forEmployee;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Long employerId) {
        this.employerId = employerId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Boolean getForEmployee() {
        return forEmployee;
    }

    public void setForEmployee(Boolean forEmployee) {
        this.forEmployee = forEmployee;
    }
}

package ar.edu.itba.paw.webapp.dto.ApplicantDto;

import javax.validation.constraints.NotNull;

public class ApplicantEditDto {

    @NotNull
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

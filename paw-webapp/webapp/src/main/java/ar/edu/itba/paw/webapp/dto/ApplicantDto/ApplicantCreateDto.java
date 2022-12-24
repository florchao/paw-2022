package ar.edu.itba.paw.webapp.dto.ApplicantDto;

import javax.validation.constraints.NotNull;

public class ApplicantCreateDto {

    @NotNull
    private Long jobId;

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}

package ar.edu.itba.paw.webapp.dto.ApplicantDto;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.webapp.dto.EmployeeDto.EmployeeDto;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ApplicantInJobsDto {
    private EmployeeDto employee;
    private String mail;

    private Long jobId;

    private URI self;

    private Integer status;

    public static ApplicantInJobsDto fromJob(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantInJobsDto dto = new ApplicantInJobsDto();
        dto.employee = EmployeeDto.fromApplicant(uriInfo, applicant.getEmployeeID());
        dto.mail = applicant.getEmployeeID().getId().getEmail();
        dto.status = applicant.getStatus();
        dto.jobId = applicant.getJobID().getJobId();

        UriBuilder selfUriBuilder = uriInfo.getBaseUriBuilder().path("/applicants").path(String.valueOf(applicant.getEmployeeID().getId().getId())).path(String.valueOf(applicant.getJobID().getJobId()));
        dto.self = selfUriBuilder.build();
        return dto;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeDto employee) {
        this.employee = employee;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}

package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Applicant;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ApplicantDto {
    private EmployeeDto employee;
    private String mail;

    private JobDto job;

    private Long jobId;

    private Integer status;

    public static ApplicantDto fromJob(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantDto dto = new ApplicantDto();
        dto.employee = EmployeeDto.fromApplicant(uriInfo, applicant.getEmployeeID());
        dto.mail = applicant.getEmployeeID().getId().getEmail();
        dto.status = applicant.getStatus();
        dto.jobId = applicant.getJobID().getJobId();

        return dto;
    }

    public static ApplicantDto fromEmployee(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantDto dto = new ApplicantDto();

        dto.job = JobDto.fromExplore(uriInfo, applicant.getJobID(), applicant.getStatus());

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

    public JobDto getJob() {
        return job;
    }

    public void setJob(JobDto job) {
        this.job = job;
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
}

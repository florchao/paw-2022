package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Applicant;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ApplicantDto {
    private EmployeeDto employee;
    private String mail;

    private JobDto job;

    private long jobId;

    private int status;

    //TODO: ver si el status se lo derivo a JobDto
    public static ApplicantDto fromJob(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantDto dto = new ApplicantDto();
        dto.employee = EmployeeDto.fromReview(uriInfo, applicant.getEmployeeID());
        dto.mail = applicant.getEmployeeID().getId().getEmail();
        dto.status = applicant.getStatus();
        dto.jobId = applicant.getJobID().getJobId();

        return dto;
    }

    public static ApplicantDto fromEmployee(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantDto dto = new ApplicantDto();

        dto.job = JobDto.fromExplore(uriInfo, applicant.getJobID());
        dto.status = applicant.getStatus();

        return dto;
    }

    public EmployeeDto getEmployee() {
        return employee;
    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}

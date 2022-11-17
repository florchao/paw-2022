package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Applicant;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class ApplicantDto {
    private EmployeeDto employee;
    private String mail;
//    private JobDto job;

    public static ApplicantDto fromJob(final UriInfo uriInfo, final Applicant applicant){
        final ApplicantDto dto = new ApplicantDto();
        dto.employee = EmployeeDto.fromReview(uriInfo, applicant.getEmployeeID());
        dto.mail = applicant.getEmployeeID().getId().getEmail();

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

}

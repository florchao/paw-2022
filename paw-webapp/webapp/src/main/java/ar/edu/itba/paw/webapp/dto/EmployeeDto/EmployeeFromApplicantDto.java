package ar.edu.itba.paw.webapp.dto.EmployeeDto;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.webapp.dto.DtoUtils;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class EmployeeFromApplicantDto {

    private String name;
    private Long id;
    private URI image;
    private URI self;
    private Long hourlyFee;

    public static EmployeeFromApplicantDto fromApplicant(final UriInfo uriInfo, final Employee employee) {
        final EmployeeFromApplicantDto dto = new EmployeeFromApplicantDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());

        dto.id = employee.getId().getId();

        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employee.getId().getId()));
        dto.image = imageUriBuilder.build();

        final UriBuilder employeeUriBuilder = uriInfo.getBaseUriBuilder().path("/employees").path(String.valueOf(employee.getId().getId()));
        dto.self = employeeUriBuilder.build();

        dto.hourlyFee = employee.getHourlyFee();

        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public Long getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(Long hourlyFee) {
        this.hourlyFee = hourlyFee;
    }
}

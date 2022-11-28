package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Employer;

import javax.ws.rs.core.UriInfo;

public class EmployerDto {
    private String name;
    private long id;

    private String email;

    public static EmployerDto fromEmployer(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());
        dto.id = employer.getId().getId();

        return dto;
    }
    public static EmployerDto fromContact(final UriInfo uriInfo, final Employer employer) {
        EmployerDto dto = EmployerDto.fromEmployer(uriInfo, employer);

        dto.email = employer.getId().getEmail();

        return dto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package ar.edu.itba.paw.webapp.dto.ReviewDto;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.webapp.dto.DtoUtils;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class UserReviewDto {
    private String role;
    private String name;
    private Long id;
    private URI image;

    public static UserReviewDto fromEmployee(final UriInfo uriInfo, final Employee employee) {
        UserReviewDto dto = new UserReviewDto();

        dto.id = employee.getId().getId();

        dto.role = "Employee";

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());

        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employee.getId().getId()));
        dto.image = imageUriBuilder.build();

        return dto;
    }

    public static UserReviewDto fromEmployer(final UriInfo uriInfo, final Employer employer) {
        UserReviewDto dto = new UserReviewDto();

        dto.id = employer.getId().getId();

        dto.role = "Employer";

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());

        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employer.getId().getId()));
        dto.image = imageUriBuilder.build();

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

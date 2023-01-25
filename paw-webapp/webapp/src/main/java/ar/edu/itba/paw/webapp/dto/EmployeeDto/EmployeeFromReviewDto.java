package ar.edu.itba.paw.webapp.dto.EmployeeDto;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.webapp.dto.DtoUtils;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class EmployeeFromReviewDto {
    private String name;
    private Long id;
    private URI image;

    public static EmployeeFromReviewDto fromReview(final UriInfo uriInfo, final Employee employee) {
        final EmployeeFromReviewDto dto = new EmployeeFromReviewDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());

        dto.id = employee.getId().getId();

        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employee.getId().getId()));
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
}

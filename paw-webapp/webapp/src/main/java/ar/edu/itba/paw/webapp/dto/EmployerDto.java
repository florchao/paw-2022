package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Employer;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class EmployerDto {
    private String name;
    private long id;

    private String email;

    private URI image;
    private URI reviews;

    private URI employeeReview;

    public static EmployerDto fromEmployer(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());
        dto.id = employer.getId().getId();

        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/image").path(String.valueOf(employer.getId().getId()));
        final UriBuilder reviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/review/employer").path(String.valueOf(employer.getId().getId()));
        final UriBuilder employeeReviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/review/").path(String.valueOf(employer.getId().getId()));


        dto.image = imageUriBuilder.build();
        dto.reviews = reviewBuilder.build();
        dto.employeeReview = employeeReviewBuilder.build();

        return dto;
    }

    public static EmployerDto fromReviews(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());

        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/image").path(String.valueOf(employer.getId().getId()));
        dto.image = imageUriBuilder.build();

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

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getReviews() {
        return reviews;
    }

    public void setReviews(URI reviews) {
        this.reviews = reviews;
    }

    public URI getEmployeeReview() {
        return employeeReview;
    }

    public void setEmployeeReview(URI employeeReview) {
        this.employeeReview = employeeReview;
    }
}

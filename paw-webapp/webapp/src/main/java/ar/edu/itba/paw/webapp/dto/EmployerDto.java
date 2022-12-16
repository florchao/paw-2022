package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Employer;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class EmployerDto {
    private String name;
    private Long id;

    private String email;

    private URI image;
    private URI reviews;

    private URI employeeReview;

    private URI delete;

    private URI jobs;

    public static EmployerDto fromEmployer(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());
        dto.id = employer.getId().getId();

        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/images").path(String.valueOf(employer.getId().getId()));
        final UriBuilder reviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employers").path(String.valueOf(employer.getId().getId())).path("reviews");
        final UriBuilder deleteBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/users").path(String.valueOf(employer.getId().getId()));
        final UriBuilder jobsBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employers").path(String.valueOf(employer.getId().getId())).path("jobs");

        dto.image = imageUriBuilder.build();
        dto.reviews = reviewBuilder.build();
        dto.delete = deleteBuilder.build();
        dto.jobs = jobsBuilder.build();

        return dto;
    }

    public static EmployerDto fromJob(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());
        dto.id = employer.getId().getId();
        final UriBuilder reviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employers").path(String.valueOf(employer.getId().getId())).path("reviews");
        dto.reviews = reviewBuilder.build();

        return dto;
    }

    public static EmployerDto fromReviews(final UriInfo uriInfo, final Employer employer) {
        final EmployerDto dto = new EmployerDto();

        dto.id = employer.getId().getId();

        dto.name = DtoUtils.firstWordsToUpper(employer.getName());

        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/images").path(String.valueOf(employer.getId().getId()));
        dto.image = imageUriBuilder.build();

        return dto;
    }
    public static EmployerDto fromContact(final UriInfo uriInfo, final Employer employer) {
        EmployerDto dto = EmployerDto.fromReviews(uriInfo, employer);

        dto.email = employer.getId().getEmail();

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

    public URI getDelete() {
        return delete;
    }

    public void setDelete(URI delete) {
        this.delete = delete;
    }

    public URI getJobs() {
        return jobs;
    }

    public void setJobs(URI jobs) {
        this.jobs = jobs;
    }
}

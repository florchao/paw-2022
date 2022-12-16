package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Employee;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class EmployeeDto {
    private String name;
    private String location;
    private Long experienceYears;

    private List<String> availabilityArr;
    private List<String> abilitiesArr;
    private Long hourlyFee;
    private long id;
    private URI self;
    private float rating;

    private Boolean isContacted;

    private URI reviews;

    private URI image;

    private URI employerReview;

    private URI ratings;

    private URI delete;

    public static EmployeeDto fromExplore(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());
        dto.experienceYears = employee.getExperienceYears();
        dto.hourlyFee = employee.getHourlyFee();

        dto.id = employee.getId().getId();
        final UriBuilder employeeUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employees").path(String.valueOf(employee.getId().getId()));
        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/images").path(String.valueOf(employee.getId().getId()));

        dto.self = employeeUriBuilder.build();
        dto.image = imageUriBuilder.build();
        return dto;
    }

    public static EmployeeDto fromExploreRating(final UriInfo uriInfo, final Employee employee, float rating, String language) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);
        dto.location = employee.nameLocation(employee.getLocation(), language);
        dto.rating = rating;
        return dto;
    }

    public static EmployeeDto fromExploreContact(final UriInfo uriInfo, final Employee employee, float rating, String language, Boolean isContacted) {
        final EmployeeDto dto = EmployeeDto.fromExploreRating(uriInfo, employee, rating, language);

        dto.isContacted = isContacted;

        return dto;
    }
    public static EmployeeDto fromProfile(final UriInfo uriInfo, final Employee employee, String language, Boolean anonymous, Boolean isContacted) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);


        dto.location = employee.nameLocation(employee.getLocation(), language);

        dto.abilitiesArr = employee.getAbilitiesArr(employee.getAbilities(), language);
        dto.availabilityArr = employee.getAvailabilityArr(employee.getAvailability(), language);

        System.out.println("ANONYMOUS: " + anonymous);

        if(!anonymous) {
            final UriBuilder reviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employees").path(String.valueOf(employee.getId().getId())).path("reviews");
            final UriBuilder employerReviewBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employees").path(String.valueOf(employee.getId().getId())).path("reviews");
            final UriBuilder ratingsUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/ratings").path(String.valueOf(employee.getId().getId()));

            dto.reviews = reviewBuilder.build();
            dto.employerReview = employerReviewBuilder.build();
            dto.ratings = ratingsUriBuilder.build();
            dto.isContacted = isContacted;
        }

        return dto;
    }

    public static EmployeeDto fromMyProfile(final UriInfo uriInfo, final Employee employee, String language) {
        final EmployeeDto dto = EmployeeDto.fromProfile(uriInfo, employee, language, false, false);
        final UriBuilder deleteUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/users").path(String.valueOf(employee.getId().getId()));

        dto.delete = deleteUriBuilder.build();

        return dto;
    }

    public static EmployeeDto fromEdit(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);

        dto.location = employee.getLocation();

        dto.abilitiesArr = employee.getAbilitiesIds(employee.getAbilities());
        dto.availabilityArr = employee.getAvailabilityIds(employee.getAvailability());


        return dto;
    }

    public static EmployeeDto fromReview(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());

        dto.id = employee.getId().getId();

        final UriBuilder imageUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/images").path(String.valueOf(employee.getId().getId()));
        dto.image = imageUriBuilder.build();

        return dto;
    }

    public static EmployeeDto fromApplicant(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromReview(uriInfo, employee);

        final UriBuilder employeeUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/employees").path(String.valueOf(employee.getId().getId()));
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

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public List<String> getAvailabilityArr() {
        return availabilityArr;
    }

    public void setAvailabilityArr(List<String> availabilityArr) {
        this.availabilityArr = availabilityArr;
    }

    public List<String> getAbilitiesArr() {
        return abilitiesArr;
    }

    public void setAbilitiesArr(List<String> abilitiesArr) {
        this.abilitiesArr = abilitiesArr;
    }

    public URI getReviews() {
        return reviews;
    }

    public void setReviews(URI reviews) {
        this.reviews = reviews;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }

    public URI getEmployerReview() {
        return employerReview;
    }

    public void setEmployerReview(URI employerReview) {
        this.employerReview = employerReview;
    }

    public URI getRatings() {
        return ratings;
    }

    public void setRatings(URI ratings) {
        this.ratings = ratings;
    }

    public URI getDelete() {
        return delete;
    }

    public void setDelete(URI delete) {
        this.delete = delete;
    }

    public Long getHourlyFee() {
        return hourlyFee;
    }

    public void setHourlyFee(Long hourlyFee) {
        this.hourlyFee = hourlyFee;
    }

    public Boolean getContacted() {
        return isContacted;
    }

    public void setContacted(Boolean contacted) {
        isContacted = contacted;
    }
}



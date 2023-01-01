package ar.edu.itba.paw.webapp.dto.EmployeeDto;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.webapp.dto.DtoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDto.class);

    public static EmployeeDto fromExplore(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());
        dto.experienceYears = employee.getExperienceYears();
        dto.hourlyFee = employee.getHourlyFee();

        dto.id = employee.getId().getId();

        LOGGER.info("URI: " + uriInfo.getAbsolutePath().toString());
        LOGGER.info("URI: " + uriInfo.getBaseUri().toString());

        final UriBuilder employeeUriBuilder = uriInfo.getBaseUriBuilder().path("/api/employee").path(String.valueOf(employee.getId().getId()));
        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/api/images").path(String.valueOf(employee.getId().getId()));

        dto.self = employeeUriBuilder.build();
        dto.image = imageUriBuilder.build();
        return dto;
    }

    public static EmployeeDto fromExploreRating(final UriInfo uriInfo, final Employee employee, float rating) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);
        dto.location = employee.nameLocation(employee.getLocation(), LocaleContextHolder.getLocale().getLanguage());
        dto.rating = rating;
        return dto;
    }

    public static EmployeeDto fromExploreContact(final UriInfo uriInfo, final Employee employee, float rating, Boolean isContacted) {
        final EmployeeDto dto = EmployeeDto.fromExploreRating(uriInfo, employee, rating);

        dto.isContacted = isContacted;

        return dto;
    }
    public static EmployeeDto fromProfile(final UriInfo uriInfo, final Employee employee, Boolean anonymous, Boolean isContacted) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);


        dto.location = employee.nameLocation(employee.getLocation(), LocaleContextHolder.getLocale().getLanguage());

        dto.abilitiesArr = employee.getAbilitiesArr(employee.getAbilities(), LocaleContextHolder.getLocale().getLanguage());
        dto.availabilityArr = employee.getAvailabilityArr(employee.getAvailability(), LocaleContextHolder.getLocale().getLanguage());

        if(!anonymous) {
            final UriBuilder reviewBuilder = uriInfo.getBaseUriBuilder().path("/api/employee").path(String.valueOf(employee.getId().getId())).path("reviews");
            final UriBuilder employerReviewBuilder = uriInfo.getBaseUriBuilder().path("/api/employee").path(String.valueOf(employee.getId().getId())).path("reviews");
            final UriBuilder ratingsUriBuilder = uriInfo.getBaseUriBuilder().path("/api/ratings").path(String.valueOf(employee.getId().getId()));

            dto.reviews = reviewBuilder.build();
            dto.employerReview = employerReviewBuilder.build();
            dto.ratings = ratingsUriBuilder.build();
            dto.isContacted = isContacted;
        }

        return dto;
    }

    public static EmployeeDto fromMyProfile(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromProfile(uriInfo, employee, false, false);
        final UriBuilder deleteUriBuilder = uriInfo.getBaseUriBuilder().path("/api/users").path(String.valueOf(employee.getId().getId()));

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

        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/api/images").path(String.valueOf(employee.getId().getId()));
        dto.image = imageUriBuilder.build();

        return dto;
    }

    public static EmployeeDto fromApplicant(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromReview(uriInfo, employee);

        final UriBuilder employeeUriBuilder = uriInfo.getBaseUriBuilder().path("/api/employee").path(String.valueOf(employee.getId().getId()));
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



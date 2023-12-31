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
    private URI ratings;
    private URI users;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDto.class);

    public static EmployeeDto fromExplore(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());
        dto.experienceYears = employee.getExperienceYears();
        dto.hourlyFee = employee.getHourlyFee();

        dto.id = employee.getId().getId();

        LOGGER.info("URI: " + uriInfo.getAbsolutePath().toString());
        LOGGER.info("URI: " + uriInfo.getBaseUri().toString());

        final UriBuilder employeeUriBuilder = uriInfo.getBaseUriBuilder().path("/employees").path(String.valueOf(employee.getId().getId()));
        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employee.getId().getId()));

        dto.self = employeeUriBuilder.build();
        dto.image = imageUriBuilder.build();
        return dto;
    }
    public static EmployeeDto fromEmployee(final UriInfo uriInfo, final Employee employee, boolean edit) {
        final EmployeeDto dto = new EmployeeDto();

        dto.name = DtoUtils.firstWordsToUpper(employee.getName());
        dto.experienceYears = employee.getExperienceYears();
        dto.hourlyFee = employee.getHourlyFee();

        dto.id = employee.getId().getId();

        LOGGER.info("URI: " + uriInfo.getAbsolutePath().toString());
        LOGGER.info("URI: " + uriInfo.getBaseUri().toString());


        final UriBuilder reviewBuilder = uriInfo.getBaseUriBuilder().path("/employees").path(String.valueOf(employee.getId().getId())).path("reviews");
        final UriBuilder ratingsUriBuilder = uriInfo.getBaseUriBuilder().path("/ratings").path(String.valueOf(employee.getId().getId()));
        final UriBuilder employeeUriBuilder = uriInfo.getBaseUriBuilder().path("/employees").path(String.valueOf(employee.getId().getId()));
        final UriBuilder imageUriBuilder = uriInfo.getBaseUriBuilder().path("/images").path(String.valueOf(employee.getId().getId()));
        final UriBuilder usersUriBuilder = uriInfo.getBaseUriBuilder().path("/users").path(String.valueOf(employee.getId().getId()));

        dto.users = usersUriBuilder.build();
        dto.self = employeeUriBuilder.build();
        dto.image = imageUriBuilder.build();
        dto.reviews = reviewBuilder.build();
        dto.ratings = ratingsUriBuilder.build();


        dto.rating = employee.getRating();

        if(!edit) {
            dto.location = employee.nameLocation(employee.getLocation(), LocaleContextHolder.getLocale().getLanguage());

            dto.abilitiesArr = employee.getAbilitiesArr(employee.getAbilities(), LocaleContextHolder.getLocale().getLanguage());
            dto.availabilityArr = employee.getAvailabilityArr(employee.getAvailability(), LocaleContextHolder.getLocale().getLanguage());
        }
        return dto;
    }


    public static EmployeeDto fromEdit(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromEmployee(uriInfo, employee, true);

        dto.location = employee.getLocation();

        dto.abilitiesArr = employee.getAbilitiesIds(employee.getAbilities());
        dto.availabilityArr = employee.getAvailabilityIds(employee.getAvailability());


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

    public URI getRatings() {
        return ratings;
    }

    public void setRatings(URI ratings) {
        this.ratings = ratings;
    }

    public URI getUsers() {
        return users;
    }

    public void setUsers(URI users) {
        this.users = users;
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



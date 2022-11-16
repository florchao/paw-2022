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
    private long experienceYears;

    private List<String> availabilityArr;
    private List<String> abilitiesArr;

    private long id;
    private URI self;

    public static EmployeeDto fromExplore(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        StringBuilder finalName = new StringBuilder();
        for (String word : employee.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        dto.name = finalName.toString();

        StringBuilder finalLocation = new StringBuilder();
        for (String word : employee.getLocation().split(" ")) {
            finalLocation.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalLocation.setLength(finalLocation.length() - 1);
        dto.location = finalLocation.toString();

        dto.experienceYears = employee.getExperienceYears();

        dto.id = employee.getId().getId();
        

        final UriBuilder employeeUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("profile/employee").path(String.valueOf(employee.getId().getId()));

        dto.self = employeeUriBuilder.build();
        return dto;
    }

    public static EmployeeDto fromProfile(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = EmployeeDto.fromExplore(uriInfo, employee);


        String language = LocaleContextHolder.getLocale().getLanguage();

        employee.nameAvailability(language);
        dto.availabilityArr = employee.getAvailabilityArr();

        employee.nameAbilities(language);
        dto.abilitiesArr = employee.getAbilitiesArr();

        return dto;
    }

    public static EmployeeDto fromReview(final UriInfo uriInfo, final Employee employee) {
        final EmployeeDto dto = new EmployeeDto();

        StringBuilder finalName = new StringBuilder();
        for (String word : employee.getName().split(" ")) {
            finalName.append(word.substring(0, 1).toUpperCase()).append(word.substring(1)).append(" ");
        }
        finalName.setLength(finalName.length() - 1);
        dto.name = finalName.toString();

        dto.id = employee.getId().getId();

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

    public long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}

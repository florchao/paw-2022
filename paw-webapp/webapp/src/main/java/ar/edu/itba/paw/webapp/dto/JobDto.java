package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Job;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

public class JobDto {

    private long jobId;

    private String title;

    private String location;

    private String description;

    private Long experienceYears;

    private List<String> availability;
    private List<String> abilities;

    private EmployerDto employerId;

    private URI self;

    private  URI applicants;

    //TODO: faltaría el status cuando lo llamo del explore, sabiendo quien es la empleada que lo ve
    public static JobDto fromExplore(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = DtoUtils.firstWordsToUpper(job.getLocation());
        dto.description = job.getDescription();

        UriBuilder jobUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/job").path(String.valueOf(job.getJobId()));
        UriBuilder applicantsUriBuilder = uriInfo.getAbsolutePathBuilder().replacePath("/api/job").path(String.valueOf(job.getJobId())).path("applicants");
        dto.self = jobUriBuilder.build();

        dto.applicants = applicantsUriBuilder.build();

        return dto;
    }

    public static JobDto fromAppliedJobs(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = DtoUtils.firstWordsToUpper(job.getLocation());

        return dto;
    }

    public static JobDto fromJob(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();
        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = DtoUtils.firstWordsToUpper(job.getLocation());

        dto.description = job.getDescription();
        dto.experienceYears = job.getExperienceYears();

        String language = LocaleContextHolder.getLocale().getLanguage();
        job.nameAbilities(language);
        dto.abilities = job.getAbilitiesArr();

        job.nameAvailability(language);
        dto.availability = job.getAvailabilityArr();

        dto.employerId = EmployerDto.fromEmployer(uriInfo, job.getEmployerId());

        return dto;

    }

    public long getJobId() {
        return jobId;
    }

    public void setJobId(long jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Long experienceYears) {
        this.experienceYears = experienceYears;
    }

    public List<String> getAvailability() {
        return availability;
    }

    public void setAvailability(List<String> availability) {
        this.availability = availability;
    }

    public List<String> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<String> abilities) {
        this.abilities = abilities;
    }

    public EmployerDto getEmployerId() {
        return employerId;
    }

    public void setEmployerId(EmployerDto employerId) {
        this.employerId = employerId;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getApplicants() {
        return applicants;
    }

    public void setApplicants(URI applicants) {
        this.applicants = applicants;
    }
}

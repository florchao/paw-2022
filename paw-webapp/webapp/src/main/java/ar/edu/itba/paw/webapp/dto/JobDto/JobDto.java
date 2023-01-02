package ar.edu.itba.paw.webapp.dto.JobDto;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.webapp.dto.DtoUtils;
import ar.edu.itba.paw.webapp.dto.EmployerDto.EmployerDto;
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

    private boolean opened;

    private List<String> availability;
    private List<String> abilities;

    private EmployerDto employerId;

    private URI self;

    private  URI applicants;

    private Integer status;

    public static JobDto fromExplore(final UriInfo uriInfo, final Job job, int status) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = job.nameLocation(job.getLocation(), LocaleContextHolder.getLocale().getLanguage());
        dto.description = job.getDescription();
        dto.status = status;

        UriBuilder jobUriBuilder = uriInfo.getBaseUriBuilder().path("/api/jobs").path(String.valueOf(job.getJobId()));
        dto.self = jobUriBuilder.build();

        return dto;
    }

    public static JobDto fromCreated(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = job.nameLocation(job.getLocation(), LocaleContextHolder.getLocale().getLanguage());

        dto.description = job.getDescription();

        UriBuilder jobUriBuilder = uriInfo.getBaseUriBuilder().path("/api/jobs").path(String.valueOf(job.getJobId()));
        UriBuilder applicantsUriBuilder = uriInfo.getBaseUriBuilder().path("/api/applicants").path(String.valueOf(job.getJobId()));
        dto.self = jobUriBuilder.build();

        dto.applicants = applicantsUriBuilder.build();

        return dto;
    }

    public static JobDto fromAppliedJobs(final UriInfo uriInfo, final Job job, String language) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());

        dto.location = job.nameLocation(job.getLocation(), language);


        return dto;
    }

    public static JobDto fromJob(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();
        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());

        dto.location = job.nameLocation(job.getLocation(), LocaleContextHolder.getLocale().getLanguage());

        dto.opened = job.isOpened();

        dto.description = job.getDescription();
        dto.experienceYears = job.getExperienceYears();


        dto.abilities = job.getAbilitiesArr(job.getAbilities(), LocaleContextHolder.getLocale().getLanguage());

        dto.availability = job.getAvailabilityArr(job.getAvailability(), LocaleContextHolder.getLocale().getLanguage());

        dto.employerId = EmployerDto.fromJob(uriInfo, job.getEmployerId());

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

    public boolean isOpened() {
        return opened;
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}

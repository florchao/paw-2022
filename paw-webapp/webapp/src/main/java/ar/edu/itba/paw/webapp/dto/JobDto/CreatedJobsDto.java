package ar.edu.itba.paw.webapp.dto.JobDto;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.webapp.dto.DtoUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CreatedJobsDto {

    private Long jobId;
    private String title;
    private String location;
    private String description;
    private URI self;
    private URI applicants;


    public static CreatedJobsDto fromCreated(final UriInfo uriInfo, final Job job) {
        final CreatedJobsDto dto = new CreatedJobsDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = job.nameLocation(job.getLocation(), LocaleContextHolder.getLocale().getLanguage());

        dto.description = job.getDescription();

        UriBuilder jobUriBuilder = uriInfo.getBaseUriBuilder().path("/jobs").path(String.valueOf(job.getJobId()));
        UriBuilder applicantsUriBuilder = uriInfo.getBaseUriBuilder().path("/applicants").path(String.valueOf(job.getJobId()));
        dto.self = jobUriBuilder.build();

        dto.applicants = applicantsUriBuilder.build();

        return dto;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
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

package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.model.Job;

import javax.ws.rs.core.UriInfo;

public class JobDto {

    private long jobId;

    private String title;

    private String location;

    private String description;

    public static JobDto fromExplore(final UriInfo uriInfo, final Job job) {
        final JobDto dto = new JobDto();

        dto.jobId = job.getJobId();
        dto.title = DtoUtils.firstWordsToUpper(job.getTitle());
        dto.location = DtoUtils.firstWordsToUpper(job.getLocation());
        dto.description = job.getDescription();

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
}

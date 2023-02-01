package ar.edu.itba.paw.webapp.dto.ApplicantDto;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.webapp.dto.JobDto.JobDto;

import javax.ws.rs.core.UriInfo;

public class JobsAppliedDto {

    private JobDto job;

    public static JobsAppliedDto fromEmployee(final UriInfo uriInfo, final Applicant applicant){
        final JobsAppliedDto dto = new JobsAppliedDto();

        dto.job = JobDto.fromAppliedJob(uriInfo, applicant.getJobID(), applicant.getStatus());

        return dto;
    }

    public JobDto getJob() {
        return job;
    }

    public void setJob(JobDto job) {
        this.job = job;
    }
}

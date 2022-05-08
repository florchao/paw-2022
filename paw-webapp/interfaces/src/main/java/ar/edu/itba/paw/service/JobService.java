package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description);
    Optional<List<Job>> getUserJobs(long employerID);
    Optional<Job> getJobByID(long jobID);
    Optional<List<Job>> getFilteredJobs(String name, Long experienceYears,String location, String availability, String abilities, Long page, long pageSize);
}

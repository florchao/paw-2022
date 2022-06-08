package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description);
    List<Job> getUserJobs(long employerID, Long page, long pageSize);
    Optional<Job> getJobByID(long jobID) throws UserNotFoundException;
    List<Job> getFilteredJobs(String name, Long experienceYears,String location, String availability, String abilities, Long page, long pageSize);
    Boolean alreadyApplied(long jobId, long employeeId);
    int getPageNumber(String name, Long experienceYears, String location, String availability, String abilities, long pageSize);
    void deleteJob(long jobId);
    void closeJob(long jobId);
    void openJob(long jobId);
    int getMyJobsPageNumber(long id, long pageSize);
}

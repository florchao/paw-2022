package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.model.exception.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface JobService {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description);
    Optional<List<Job>> getUserJobs(Employer employerID);
    Optional<Job> getJobByID(long jobID) throws UserNotFoundException;
    Optional<List<Job>> getFilteredJobs(String name, Long experienceYears,String location, String availability, String abilities, Long page, long pageSize);
    Optional<Boolean> alreadyApplied(long jobId, long employeeId);
    int getPageNumber(String name, Long experienceYears, String location, String availability, String abilities, long pageSize);
    String getJobNameById(long jobID);
    void deleteJob(long jobId);
    void closeJob(long jobId);
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description);
    Optional<List<Job>> getUserJobs(Employer employerID, Long page, long pageSize);
    Optional<Job> getJobById(long jobId);
    Optional<Boolean> alreadyApplied(long jobId, long employeeId);
    Optional<List<Job>> getAllActiveJobs(long pageSize);
    Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize);
    int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize);
    String getJobNameById(long jobID);
    void deleteJob(long jobId);
    void closeJob(long jobId);
    void openJob(long jobId);
    int getMyJobsPageNumber(long id, long pageSize);
}

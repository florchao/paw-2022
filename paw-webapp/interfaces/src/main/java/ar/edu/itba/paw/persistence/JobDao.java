package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description);
    Optional<List<Job>> getUserJobs(long employerID);
    Optional<Job> getJobById(long jobId);
    Optional<Boolean> alreadyApplied(long jobId, long employeeId);
    Optional<List<Job>> getAllJobs(long pageSize);
    Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize);
    int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize);
    String getJobNameById(long jobID);
}

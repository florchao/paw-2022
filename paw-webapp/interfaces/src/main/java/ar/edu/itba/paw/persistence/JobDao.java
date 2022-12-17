package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Job create(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description);
    List<Job> getUserJobs(Employer employerID,  Long page, long pageSize);
    Optional<Job> getJobById(long jobId);
    Boolean alreadyApplied(Job jobId, Employee employeeId);
    List<Job> getAllActiveJobs(long pageSize);
    List<Job> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize);
    int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize);
    void deleteJob(long jobId);
    Optional<Job> closeJob(long jobId);
    Optional<Job> openJob(long jobId);
    int getMyJobsPageNumber(Employer id, long pageSize);
}

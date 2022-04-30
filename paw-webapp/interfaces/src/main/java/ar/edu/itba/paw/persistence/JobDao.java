package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface JobDao {
    Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities);
    Optional<List<Job>> getUserJobs(long employerID);
}

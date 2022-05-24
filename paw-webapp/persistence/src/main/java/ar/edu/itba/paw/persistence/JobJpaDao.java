package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Job;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JobJpaDao implements  JobDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description) {
        return null;
    }

    @Override
    public Optional<List<Job>> getUserJobs(long employerID) {
        return Optional.empty();
    }

    @Override
    public Optional<Job> getJobById(long jobId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Job>> getAllJobs(long pageSize) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize) {
        return Optional.empty();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        return 0;
    }

    @Override
    public String getJobNameById(long jobID) {
        return null;
    }
}

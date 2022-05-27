package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.type.IntegerType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class JobJpaDao implements JobDao{

    @PersistenceContext
    private EntityManager em;
    @Override
    public Job create(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        final Job job = new Job(title, location, employerId, availability, experienceYears, abilities, description);
        em.persist(job);
        return job;
    }

    @Override
    public Optional<List<Job>> getUserJobs(Employer employerID) {

        final TypedQuery<Job> query = em.createQuery("select u from Job u where u.employerId =:employerId", Job.class);
        query.setParameter("employerId", employerID);
        return Optional.of(query.getResultList());
    }

    @Override
    public Optional<Job> getJobById(long jobId) {
        return Optional.of(em.find(Job.class, jobId));
    }

    @Override
    public Optional<List<Job>> getAllJobs(long pageSize) {
        final TypedQuery<Job> jobList = em.createQuery("select u from Job u", Job.class);
        return Optional.of(jobList.getResultList());
    }

    @Override
    public Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize) {
        return getAllJobs(pageSize);
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        return 0;
    }

    @Override
    public String getJobNameById(long jobID) {
       Job job = em.find(Job.class, jobID);
       return job.getTitle();
    }
}

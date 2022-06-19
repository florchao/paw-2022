package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class JobJpaDao implements  JobDao{

    @PersistenceContext
    private EntityManager em;
    @Override
    public Job create(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        final Job job = new Job(title, location, employerId, availability, experienceYears, abilities, description);
        em.persist(job);
        return job;
    }

    @Override
    public List<Job> getUserJobs(Employer employerID, Long page, long pageSize) {
        final TypedQuery<Job> query = em.createQuery("select u from Job u where u.employerId =:employerId", Job.class).setFirstResult((int) (page * pageSize)).setMaxResults((int) pageSize);
        query.setParameter("employerId", employerID);
        return query.getResultList();
    }

    @Override
    public Optional<Job> getJobById(long jobId) {
        return Optional.ofNullable(em.find(Job.class, jobId));
    }

    @Override
    public Boolean alreadyApplied(Job jobId, Employee employeeId) {
        TypedQuery<Applicant> typedQuery = em.createQuery("select a from Applicant a where a.employeeID =:employee and a.jobID =:job", Applicant.class);
        typedQuery.setParameter("employee", employeeId);
        typedQuery.setParameter("job", jobId);
        return !typedQuery.getResultList().isEmpty();
    }

    @Override
    public List<Job> getAllActiveJobs(long pageSize) {
        final TypedQuery<Job> jobList = em.createQuery("select e from Job e where e.opened=TRUE", Job.class);
        return jobList.setMaxResults((int)pageSize).getResultList();
    }

    @Override
    public List<Job> getFilteredJobs(String name, Long experienceYears, String location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT e FROM Job e where e.opened=TRUE and ");
        if (name != null) {
            stringBuilder.append("e.title like '%").append(name.toLowerCase()).append("%'");
            if (location == null) {
                stringBuilder.append(" or e.location like '%").append(name.toLowerCase()).append("%' ");
                stringBuilder.append(" and ");
            } else {
                stringBuilder.append(" and ");
            }
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= ").append(experienceYears);
            stringBuilder.append(" and ");
        }
        if (location != null) {
            stringBuilder.append("e.location like '%").append(location.toLowerCase()).append("%' ");
            stringBuilder.append(" and ");
        }
        for (String av : availabilityList) {
            stringBuilder.append("e.availability like '%").append(av).append("%'");
            stringBuilder.append(" and ");
        }
        for (String ability : abilitiesList) {
            stringBuilder.append("e.abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 4);
        TypedQuery<Job> filteredQuery = em.createQuery(stringBuilder.toString(), Job.class).setFirstResult((int) (page * pageSize)).setMaxResults((int) pageSize);
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("SELECT count(j) FROM Job j where j.opened=TRUE and ");
        if (name != null) {
            strBuilder.append("j.title like '%").append(name.toLowerCase()).append("%'");
            strBuilder.append(" and ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            strBuilder.append("j.experienceYears >= ").append(experienceYears);
            strBuilder.append(" and ");
        }
        if (location != null) {
            strBuilder.append("j.location like '%").append(location.toLowerCase()).append("%' ");
            strBuilder.append(" and ");
        }
        for (String av : availability) {
            strBuilder.append("j.availability like '%").append(av).append("%'");
            strBuilder.append(" and ");
        }
        for (String ability : abilities) {
            strBuilder.append("j.abilities like '%").append(ability).append("%'");
            strBuilder.append(" and ");
        }
        strBuilder.setLength(strBuilder.length() - 4);

        TypedQuery<Long> filteredQuery = em.createQuery(strBuilder.toString(), Long.class);

        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public void deleteJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        if(!job.isPresent()) return;
        em.remove(job.get());
    }

    @Override
    public void closeJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        if(!job.isPresent()) return;
        job.get().setOpened(false);
    }

    @Override
    public void openJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        if(!job.isPresent()) return;
        job.get().setOpened(true);
    }

    @Override
    public int getMyJobsPageNumber(Employer id, long pageSize) {
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(j) FROM Job j WHERE j.employerId =:employer", Long.class);
        filteredQuery.setParameter("employer", id);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }
}

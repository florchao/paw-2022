package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        Map<String, Object> paramMap = new HashMap<>();
        stringBuilder.append("SELECT e FROM Job e where e.opened=TRUE and ");
        if (name != null) {
            if (location == null) {
                stringBuilder.append("(e.title like :title or e.location like :titleLocation)");
                paramMap.put("title", '%' + name.toLowerCase() + '%');
                paramMap.put("titleLocation", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and ");
            } else {
                stringBuilder.append("e.title like :title ");
                paramMap.put("title", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and ");
            }
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and ");
        }
        if (location != null) {
            stringBuilder.append("e.location like :location ");
            paramMap.put("location", '%' + location.toLowerCase() + '%');
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
        for (String key : paramMap.keySet()) {
            filteredQuery.setParameter(key, paramMap.get(key));
        }
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        stringBuilder.append("SELECT count(e) FROM Job e where e.opened=TRUE and ");
        if (name != null) {
            if (location == null) {
                stringBuilder.append("(e.title like :title or e.location like :titleLocation)");
                paramMap.put("title", '%' + name.toLowerCase() + '%');
                paramMap.put("titleLocation", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and ");
            } else {
                stringBuilder.append("e.title like :title ");
                paramMap.put("title", '%' + name.toLowerCase() + '%');
                stringBuilder.append(" and ");
            }
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears >= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and ");
        }
        if (location != null) {
            stringBuilder.append("e.location like :location ");
            paramMap.put("location", '%' + location.toLowerCase() + '%');
            stringBuilder.append(" and ");
        }
        for (String av : availability) {
            stringBuilder.append("e.availability like '%").append(av).append("%'");
            stringBuilder.append(" and ");
        }
        for (String ability : abilities) {
            stringBuilder.append("e.abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 4);
        System.out.println("en page number: "+ stringBuilder.toString());
        TypedQuery<Long> filteredQuery = em.createQuery(stringBuilder.toString(), Long.class);
        for (String key : paramMap.keySet()) {
            filteredQuery.setParameter(key, paramMap.get(key));
        }

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

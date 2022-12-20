package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JobJpaDao implements JobDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Job create(String title, String location, Employer employerId, String availability, long experienceYears, String abilities, String description) {
        final Job job = new Job(title.toLowerCase(), location.toLowerCase(), employerId, availability, experienceYears, abilities, description);
        em.persist(job);
        return job;
    }

    @Override
    public List<Job> getUserJobs(Employer employerID, Long page, long pageSize) {

        final Query idQuery = em.createNativeQuery("SELECT jobid FROM jobs where employerid =:employer LIMIT :pageSize OFFSET :offset");
        idQuery.setParameter("pageSize", pageSize);
        idQuery.setParameter("offset", page * pageSize);
        idQuery.setParameter("employer", employerID);

        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) idQuery.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());

        if (ids.isEmpty()) {
            return new ArrayList<>();
        }

        // noinspection JpaQlInspection
        final TypedQuery<Job> query = em.createQuery("select j from Job j where jobid in :ids", Job.class);
        query.setParameter("ids", ids);
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
        return jobList.setMaxResults((int) pageSize).getResultList();
    }

    @Override
    public List<Job> getFilteredJobs(String name, Long experienceYears, List<String> location, List<String> availabilityList, List<String> abilitiesList, Long page, long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();

        stringBuilder.append("SELECT jobid FROM jobs where opened=TRUE and ");
        if (name != null) {
            stringBuilder.append("lower(title) like :title ");
            paramMap.put("title", '%' + name.toLowerCase() + '%');
            stringBuilder.append(" and ");

        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("experienceYears <= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and ");
        }
        String variableCount = "a";
        for (String av : availabilityList) {
            stringBuilder.append("availability like ").append(":availability").append(variableCount).append(" ");
            paramMap.put("availability" + variableCount, '%' + av + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" and ");
        }
        if (!location.isEmpty())
            stringBuilder.append(" ( ");
        for (String l : location) {
            stringBuilder.append("location like ").append(":location").append(variableCount).append(" ");
            paramMap.put("location" + variableCount, '%' + l + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" or   ");
        }
        if (!location.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 7);
            stringBuilder.append(" ) and ");
        }
        for (String ability : abilitiesList) {
            stringBuilder.append("abilities like ").append(":abilities").append(variableCount).append(" ");
            paramMap.put("abilities" + variableCount, '%' + ability + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 4);
        stringBuilder.append(" LIMIT :pageSize OFFSET :offset");

        final Query idQuery = em.createNativeQuery(stringBuilder.toString());
        for (String key : paramMap.keySet()) {
            idQuery.setParameter(key, paramMap.get(key));
        }
        idQuery.setParameter("pageSize", pageSize);
        idQuery.setParameter("offset", page * pageSize);
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) idQuery.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return new ArrayList<>();
        }
//        noinspection JpaQlInspection
        TypedQuery<Job> filteredQuery = em.createQuery("select j from Job j where jobid in :ids", Job.class);
        filteredQuery.setParameter("ids", ids);
        return filteredQuery.getResultList();
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, List<String> location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        Map<String, Object> paramMap = new HashMap<>();
        stringBuilder.append("SELECT count(e) FROM Job e where e.opened=TRUE and ");
        if (name != null) {
            stringBuilder.append("lower(e.title) like :title ");
            paramMap.put("title", '%' + name.toLowerCase() + '%');
            stringBuilder.append(" and ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("e.experienceYears <= :experienceYears ");
            paramMap.put("experienceYears", experienceYears);
            stringBuilder.append(" and ");
        }
        String variableCount = "a";
        for (String av : availability) {
            stringBuilder.append("e.availability like ").append(":availability").append(variableCount).append(" ");
            paramMap.put("availability" + variableCount, '%' + av + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" and ");
        }
        if (!location.isEmpty())
            stringBuilder.append(" ( ");
        for (String l : location) {
            stringBuilder.append("e.location like ").append(":location").append(variableCount).append(" ");
            paramMap.put("location" + variableCount, '%' + l + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" or ");
        }
        if (!location.isEmpty()) {
            stringBuilder.setLength(stringBuilder.length() - 4);
            stringBuilder.append(" ) and ");
        }
        for (String ability : abilities) {
            stringBuilder.append("e.abilities like ").append(":abilities").append(variableCount).append(" ");
            paramMap.put("abilities" + variableCount, '%' + ability + '%');
            variableCount = String.valueOf((char) (variableCount.charAt(0) + 1));
            stringBuilder.append(" and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 4);
        TypedQuery<Long> filteredQuery = em.createQuery(stringBuilder.toString(), Long.class);
        for (String key : paramMap.keySet()) {
            filteredQuery.setParameter(key, paramMap.get(key));
        }

        return (int) Math.ceil((double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public void deleteJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        if (!job.isPresent()) return;
        em.remove(job.get());
    }

    @Override
    public Optional<Job> closeJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        job.ifPresent(value -> value.setOpened(false));
        return job;
    }

    @Override
    public Optional<Job> openJob(long jobId) {
        Optional<Job> job = getJobById(jobId);
        job.ifPresent(value -> value.setOpened(true));
        return job;
    }

    @Override
    public int getMyJobsPageNumber(Employer id, long pageSize) {
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(j) FROM Job j WHERE j.employerId =:employer", Long.class);
        filteredQuery.setParameter("employer", id);
        return (int) Math.ceil((double) filteredQuery.getSingleResult() / pageSize);
    }
}

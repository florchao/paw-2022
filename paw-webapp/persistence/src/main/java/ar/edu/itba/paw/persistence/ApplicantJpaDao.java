package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ApplicantJpaDao implements ApplicantDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Applicant create(Job jobID, Employee employeeID) {
            final Applicant applicant = new Applicant(jobID, employeeID);
            em.persist(applicant);
            return applicant;
    }

    @Override
    public List<Applicant> getAppliedJobsByApplicant(Employee employeeID, Long page, int pageSize) {
        final Query idQuery = em.createNativeQuery("SELECT jobid FROM applicants where employeeid =:employee LIMIT :pageSize OFFSET :offset");
        idQuery.setParameter("pageSize", pageSize);
        idQuery.setParameter("offset", page * pageSize);
        idQuery.setParameter("employee", employeeID);
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) idQuery.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        // noinspection JpaQlInspection
        final TypedQuery<Applicant> query = em.createQuery("select a from Applicant a where jobid in :ids and a.employeeID =:employee", Applicant.class);
        query.setParameter("ids", ids);
        query.setParameter("employee", employeeID);
        return query.getResultList();
    }

    @Override
    public int getPageNumberForAppliedJobs(Employee employee, int pageSize) {
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(a) FROM Applicant a WHERE a.employeeID =:employee", Long.class);
        filteredQuery.setParameter("employee", employee);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);
    }

    @Override
    public List<Applicant> getApplicantsByJob(Job jobID, Long page, int pageSize) {

        final Query idQuery = em.createNativeQuery("SELECT employeeid FROM applicants where jobid =:job order by status LIMIT :pageSize OFFSET :offset");
        idQuery.setParameter("pageSize", pageSize);
        idQuery.setParameter("offset", page * pageSize);
        idQuery.setParameter("job", jobID);
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) idQuery.getResultList().stream().map(o -> ((Integer) o).longValue()).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        // noinspection JpaQlInspection
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u where employeeid in :ids and u.jobID =:jobID order by u.status", Applicant.class);
        query.setParameter("jobID", jobID);
        query.setParameter("ids", ids);
        return (query.getResultList());
    }

    @Override
    public Boolean existsApplicant(Employee employeeId, Job jobId) {
        TypedQuery<Applicant> contactTypedQuery = em.createQuery("SELECT c FROM Applicant c WHERE c.employeeID =:employee AND c.jobID = :jobID", Applicant.class);
        contactTypedQuery.setParameter("jobID", jobId);
        contactTypedQuery.setParameter("employee", employeeId);
        return!contactTypedQuery.getResultList().isEmpty();
    }

    @Override
    public int getPageNumber(Job jobID, int pageSize) {
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(a) FROM Applicant a WHERE a.jobID =:job", Long.class);
        filteredQuery.setParameter("job", jobID);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);

    }

    @Override
    public int getStatus(Employee employee, Job job) {
        TypedQuery<Applicant> query = em.createQuery("SELECT a FROM Applicant a WHERE a.employeeID =:employee AND a.jobID = :job", Applicant.class);
        query.setParameter("job", job);
        query.setParameter("employee", employee);
        return query.getSingleResult().getStatus();
    }

    @Override
    public void deleteApplication(Applicant applicant) {
        em.remove(applicant);
    }

    @Override
    public Optional<Applicant> getApplicant(Employee employeeId, Job jobId){
        TypedQuery<Applicant> contactTypedQuery = em.createQuery("SELECT c FROM Applicant c WHERE c.employeeID =:employee AND c.jobID = :jobID", Applicant.class);
        contactTypedQuery.setParameter("jobID", jobId);
        contactTypedQuery.setParameter("employee", employeeId);
        return Optional.ofNullable(contactTypedQuery.getSingleResult());
    }

    @Override
    public void rejectApplications(Job jobId) {
        Query contactQuery = em.createQuery("UPDATE Applicant a SET a.status =:newStatus WHERE a.jobID = :job");
        contactQuery.setParameter("job", jobId);
        contactQuery.setParameter("newStatus", 2);
        contactQuery.executeUpdate();
    }

    @Override
    public void deleteApplicationsFromJob(Job jobId) {
        Query contactQuery = em.createQuery("DELETE FROM Applicant a WHERE a.jobID = :job");
        contactQuery.setParameter("job", jobId);
        contactQuery.executeUpdate();
    }

    @Override
    public int changeStatus(int status, Employee employee, Job job) {
        Query contactQuery = em.createQuery("UPDATE Applicant a SET a.status =:newStatus WHERE a.employeeID =:employee AND a.jobID = :job");
        contactQuery.setParameter("job", job);
        contactQuery.setParameter("employee", employee);
        contactQuery.setParameter("newStatus", status);
        contactQuery.executeUpdate();
        return status;
    }
}

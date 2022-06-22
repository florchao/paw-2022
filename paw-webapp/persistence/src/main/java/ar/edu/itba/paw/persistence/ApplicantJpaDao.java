package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

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
    public List<Job> getJobsByApplicant(Employee employeeID, Long page, int pageSize) {
        final TypedQuery<Job> query = em.createQuery("select a.jobID from Applicant a where a.employeeID =:employeeID", Job.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
        query.setParameter("employeeID", employeeID);
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
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u where u.jobID =:jobID order by u.status", Applicant.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
        query.setParameter("jobID", jobID);
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
    public int changeStatus(int status, Employee employee, Job job) {
        Query contactQuery = em.createQuery("UPDATE Applicant a SET a.status =:newStatus WHERE a.employeeID =:employee AND a.jobID = :job");
        contactQuery.setParameter("job", job);
        contactQuery.setParameter("employee", employee);
        contactQuery.setParameter("newStatus", status);
        contactQuery.executeUpdate();
        return status;
    }
}

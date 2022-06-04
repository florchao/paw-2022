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
import java.util.Optional;

@Repository
public class ApplicantJpaDao implements ApplicantDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Applicant create(long jobID, long employeeID) {
        Optional<Job> job = Optional.ofNullable(em.find(Job.class, jobID));
        Optional<Employee> employee = Optional.ofNullable(em.find(Employee.class, employeeID));
        if(job.isPresent() && employee.isPresent()) {
            final Applicant applicant = new Applicant(job.get(), employee.get());
            em.persist(applicant);
            return applicant;
        }
        return null;
    }

    @Override
    public Optional<List<Job>> getJobsByApplicant(Employee employeeID) {
        final TypedQuery<Job> query = em.createQuery("select a.jobID from Applicant a where a.employeeID =:employeeID", Job.class);
        query.setParameter("employeeID", employeeID);
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Optional<List<Applicant>> getApplicantsByJob(Job jobID, Long page, int pageSize) {
        System.out.println("mi page y pageSize son: "+ page + " " + pageSize);
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u where u.jobID =:jobID", Applicant.class).setFirstResult((int) (page * pageSize)).setMaxResults(pageSize);
        query.setParameter("jobID", jobID);
        return Optional.ofNullable(query.getResultList());
    }

    @Override
    public Optional<Boolean> existsApplicant(Employee employeeId, Job jobId) {
        TypedQuery<Applicant> contactTypedQuery = em.createQuery("SELECT c FROM Applicant c WHERE c.employeeID =:employee AND c.jobID = :jobID", Applicant.class);
        contactTypedQuery.setParameter("jobID", jobId);
        contactTypedQuery.setParameter("employee", employeeId);
        return Optional.of(!contactTypedQuery.getResultList().isEmpty());
    }

    @Override
    public int getPageNumber(long jobID, int pageSize) {
        Job job = em.find(Job.class, jobID);
        TypedQuery<Long> filteredQuery = em.createQuery("SELECT count(a) FROM Applicant a WHERE a.jobID =:job", Long.class);
        filteredQuery.setParameter("job", job);
        return (int) Math.ceil( (double) filteredQuery.getSingleResult() / pageSize);

    }

    @Override
    public int changeStatus(int status, Employee employee, Job job) {
        System.out.println("CHANGE DAO");
        System.out.println(employee.getId());
        System.out.println(job.getJobId());
        Query contactQuery = em.createQuery("UPDATE Applicant a SET a.status =:newStatus WHERE a.employeeID =:employee AND a.jobID = :job");
        contactQuery.setParameter("job", job);
        contactQuery.setParameter("employee", employee);
        contactQuery.setParameter("newStatus", status);
        contactQuery.executeUpdate();
        return status;
    }
}

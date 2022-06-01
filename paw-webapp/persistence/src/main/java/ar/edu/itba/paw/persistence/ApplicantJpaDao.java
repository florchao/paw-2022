package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    //TODO hay que adaptarla
    @Override
    public Optional<List<Applicant>> getApplicantsByJob(Job jobID, Long page, int pageSize) {
        //    List<Applicant> query = jdbcTemplate.query("SELECT employeeid, jobid, name, email FROM applicants NATURAL " +
        //                        "JOIN employee JOIN users ON employee.employeeid = users.userid WHERE jobid = ? LIMIT ? OFFSET ?",
        //                new Object[] {jobID,pageSize, page*pageSize}, APPLI_LIST_ROW_MAPPER);
        //        return Optional.of(query);
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u where u.jobID =:jobID", Applicant.class);
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

    //TODO hay que hacerla
    @Override
    public int getPageNumber(long jobID, int pageSize) {
        return 0;
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Contact;
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
    public Optional<List<Applicant>> getApplicantsByJob(Job jobID) {
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u where u.jobID =:jobID", Applicant.class);
        query.setParameter("jobID", jobID);
        return Optional.of(query.getResultList());
    }

    @Override
    public Optional<Applicant> getInfoMail(Job jobID) {
        final TypedQuery<Applicant> query = em.createQuery("select u from Applicant u join Employer e where u.jobID =:jobID and e.id=:employerID", Applicant.class);
        query.setParameter("jobID", jobID);
        query.setParameter("employerID", jobID.getEmployerId().getId());
        return Optional.of(query.getSingleResult());
    }

    @Override
    public Optional<Boolean> existsApplicant(long employeeId, long jobId) {
        TypedQuery<Applicant> contactTypedQuery = em.createQuery("SELECT c FROM Applicant c WHERE c.employeeID = :employee AND c.jobID = :jobID", Applicant.class);
        contactTypedQuery.setParameter("jobID", jobId);
        contactTypedQuery.setParameter("employee", employeeId);
        return Optional.of(!contactTypedQuery.getResultList().isEmpty());

    }
}

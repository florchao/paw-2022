package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicantJpaDao implements ApplicantDao{
    @PersistenceContext
    private EntityManager em;

    @Override
    public Applicant create(long jobID, long employeeID) {
        return null;
    }

    @Override
    public Optional<List<Applicant>> getApplicantsByJob(long jobID) {
        return Optional.empty();
    }

    @Override
    public Optional<Applicant> getInfoMail(long jobID) {
        return Optional.empty();
    }

    @Override
    public Optional<Boolean> existsApplicant(long employeeId, long jobId) {
        return Optional.empty();
    }
}

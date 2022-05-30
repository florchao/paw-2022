package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;

import java.util.List;
import java.util.Optional;

public interface ApplicantDao {
    Applicant create(long jobID, long employeeID);
    Optional<List<Applicant>> getApplicantsByJob(long jobID, Long page, int pageSize);
    int getPageNumber(long jobID, int pageSize);
    Optional<Applicant> getInfoMail(long jobID);
    Optional<Boolean> existsApplicant(long employeeId, long jobId);
}

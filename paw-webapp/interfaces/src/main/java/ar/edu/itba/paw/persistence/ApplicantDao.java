package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface ApplicantDao {
    Applicant create(long jobID, long employeeID);
    Optional<List<Applicant>> getApplicantsByJob(Job jobID);
    Optional<Boolean> existsApplicant(Employee employeeId, Job jobId);
}

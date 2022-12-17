package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ApplicantService {
    Applicant create(long jobID, long employeeID) throws AlreadyExistsException;
    List<Applicant> getApplicantsByJob(long jobID, Long page, int pageSize);
    int getPageNumber(long jobID, int pageSize);
    void apply(long jobID, long employeeID) throws AlreadyExistsException;
    List<Applicant> getAppliedJobsByApplicant(long employeeID, Long page, int pageSize);
    int getPageNumberForAppliedJobs(Long employeeId, int pageSize);

    int changeStatus(int status, long employeeId, long jobId);

    int getStatus(long employeeId, long jobId);

    void withdrawApplication(long employeeId, long jobId);

    void withdrawAppsFromJob(Optional<Job> job);

    void rejectAppsfromJob(Optional<Job> job);
}

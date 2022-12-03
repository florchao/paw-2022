package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;

import java.util.List;
import java.util.Optional;

public interface ApplicantDao {
    Applicant create(Job jobID, Employee employeeID);

    List<Applicant> getApplicantsByJob(Job jobID, Long page, int pageSize);

    List<Applicant> getAppliedJobsByApplicant(Employee employeeID, Long page, int pageSize);

    int changeStatus(int status, Employee employee, Job job);

    Boolean existsApplicant(Employee employeeId, Job jobId);

    int getPageNumber(Job jobID, int pageSize);

    int getPageNumberForAppliedJobs(Employee employee, int pageSize);

    int getStatus(Employee employee, Job job);

    void deleteApplication(Applicant applicant);

    Optional<Applicant> getApplicant(Employee employeeId, Job jobId);

}

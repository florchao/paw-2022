package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;

import java.util.List;
import java.util.Optional;

public interface ApplicantService {
    Applicant create(long jobID, long employeeID);
    Optional<List<Applicant>> getApplicantsByJob(long jobID);
    void apply(long jobID, String employeeUsername, long employerID);
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.persistence.ApplicantDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements ApplicantService{
    @Autowired
    ApplicantDao applicantDao;

    @Autowired
    MailingService mailingService;

    @Override
    public Applicant create(long jobID, long employeeID) {
        return applicantDao.create(jobID, employeeID);
    }

    @Override
    public Optional<List<Applicant>> getApplicantsByJob(long jobID) {
        return applicantDao.getApplicantsByJob(jobID);
    }

    @Override
    public void apply(long jobID, String employeeUsername, long employerID) {

    }
}

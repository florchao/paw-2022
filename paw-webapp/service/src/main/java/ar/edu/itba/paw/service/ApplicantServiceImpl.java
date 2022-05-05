package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.persistence.ApplicantDao;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements ApplicantService{
    @Autowired
    ApplicantDao applicantDao;

    @Autowired
    EmployeeDao employeeDao;

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
    public void apply(long jobID, User user) {
        Optional<Applicant> applicant = applicantDao.getInfoMail(jobID);
        Optional<Employee> employee = employeeDao.getEmployeeById(user.getId());
        mailingService.sendApplyMail(applicant.get().getEmployerUsername(), applicant.get().getJobName(), employee.get().getName());
        create(jobID, user.getId());
    }
}

package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.persistence.ApplicantDao;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements ApplicantService{
    @Autowired
    ApplicantDao applicantDao;
    @Autowired
    JobDao jobDao;

    @Autowired
    EmployeeDao employeeDao;

    @Autowired
    MailingService mailingService;

    @Transactional
    @Override
    public Applicant create(long jobID, long employeeID) {
        Optional<Boolean> exists = applicantDao.existsApplicant(employeeID, jobID);
        if(exists.isPresent() && exists.get())
            throw new AlreadyExistsException("You already applied for this job");
        return applicantDao.create(jobID, employeeID);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Applicant>> getApplicantsByJob(long jobID) {
        Optional<Job> job = jobDao.getJobById(jobID);
        return job.map(value -> applicantDao.getApplicantsByJob(value)).orElse(null);
    }

    @Override
    public void apply(long jobID, User user) {
        Optional<Job> job = jobDao.getJobById(jobID);
        if (job.isPresent()) {
            Optional<Applicant> applicant = applicantDao.getInfoMail(job.get());
            Optional<Employee> employee = employeeDao.getEmployeeById(user.getId());
            if (applicant.isPresent() && employee.isPresent())
                mailingService.sendApplyMail(applicant.get().getJobID().getEmployerId().getName(), applicant.get().getJobID().getTitle(), employee.get().getName(), jobID);
            create(jobID, user.getId());
        }
    }
}

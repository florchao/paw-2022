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
        Optional<Job> job = jobDao.getJobById(jobID);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
        if(employee.isPresent() && job.isPresent()) {
            Optional<Boolean> exists = applicantDao.existsApplicant(employee.get(), job.get());
            if (exists.isPresent() && exists.get())
                throw new AlreadyExistsException("You already applied for this job");
            return applicantDao.create(jobID, employeeID);
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<List<Job>> getJobsByApplicant(long employeeID) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
        return employee.flatMap(value -> applicantDao.getJobsByApplicant(value));
    }

    //todo hay que adaptarla con los parmetros
    @Transactional(readOnly = true)
    @Override
    public Optional<List<Applicant>> getApplicantsByJob(long jobID, Long page, int pageSize) {
        Optional<Job> job = jobDao.getJobById(jobID);
        return job.map(value -> applicantDao.getApplicantsByJob(value, (long) 0, 0)).orElse(null);
    }

    @Override
    public int getPageNumber(long jobID, int pageSize) {
        return applicantDao.getPageNumber(jobID, pageSize);
    }

    @Transactional
    @Override
    public void apply(long jobID, User user) {
        Optional<Job> job = jobDao.getJobById(jobID);
        if (job.isPresent()) {
            Optional<Employee> employee = employeeDao.getEmployeeById(user.getId());
            employee.ifPresent(value -> mailingService.sendApplyMail(job.get().getEmployerId().getName(), job.get().getTitle(), value.getName(), jobID));
            create(jobID, user.getId());
        }
    }

    @Transactional
    @Override
    public int changeStatus(int status, long employeeId, long jobId) {
        Optional<Job> job = jobDao.getJobById(jobId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        System.out.println("en change status service");
        if(job.isPresent() && employee.isPresent())
            return applicantDao.changeStatus(status, employee.get(), job.get());
        return -1;
    }
}

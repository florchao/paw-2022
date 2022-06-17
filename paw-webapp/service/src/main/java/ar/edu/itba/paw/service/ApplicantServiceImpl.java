package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.model.exception.AlreadyExistsException;
import ar.edu.itba.paw.persistence.ApplicantDao;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicantServiceImpl implements ApplicantService{
    @Autowired
    private ApplicantDao applicantDao;
    @Autowired
    private JobDao jobDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private MailingService mailingService;

    @Transactional
    @Override
    public Applicant create(long jobID, long employeeID) {
        Optional<Job> job = jobDao.getJobById(jobID);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
        if(employee.isPresent() && job.isPresent()) {
            Boolean exists = applicantDao.existsApplicant(employee.get(), job.get());
            if (exists)
                throw new AlreadyExistsException("You already applied for this job");
            return applicantDao.create(job.get(), employee.get());
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> getJobsByApplicant(long employeeID, Long page, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
       if(employee.isPresent()){
           return applicantDao.getJobsByApplicant(employee.get(), page, pageSize);
       }
       return Collections.emptyList();
    }

    @Override
    public int getPageNumberForAppliedJobs(Long employeeId, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        return employee.map(value -> applicantDao.getPageNumberForAppliedJobs(value, pageSize)).orElse(0);
    }

    //todo hay que adaptarla con los parmetros
    @Transactional(readOnly = true)
    @Override
    public List<Applicant> getApplicantsByJob(long jobID, Long page, int pageSize) {
        Optional<Job> job = jobDao.getJobById(jobID);
        return job.map(value -> applicantDao.getApplicantsByJob(value, page, pageSize)).orElse(Collections.emptyList());
    }

    @Override
    public int getPageNumber(long jobID, int pageSize) {
        Optional<Job> job = jobDao.getJobById(jobID);
        return job.map(value -> applicantDao.getPageNumber(value, pageSize)).orElse(0);
    }

    @Transactional
    @Override
    public void apply(long jobID, User user) {
        Optional<Job> job = jobDao.getJobById(jobID);
        if (job.isPresent()) {
            Optional<Employee> employee = employeeDao.getEmployeeById(user.getId());
            Employer employer= job.get().getEmployerId();
            employer.firstWordsToUpper();
            if(employee.isPresent()) {
                employee.get().firstWordsToUpper();
                mailingService.sendApplyMail(employer.getName(), job.get().getTitle(), employee.get().getName(), jobID);
            }
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

    @Transactional
    @Override
    public int getStatus(long employeeId, long jobId) {
        Optional<Job> job = jobDao.getJobById(jobId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if(job.isPresent() && employee.isPresent()) {
            return applicantDao.getStatus(employee.get(), job.get());
        }
        return -1;
    }
}

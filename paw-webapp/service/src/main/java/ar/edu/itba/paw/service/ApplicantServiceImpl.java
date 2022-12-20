package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
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
public class ApplicantServiceImpl implements ApplicantService {
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
    public Applicant create(long jobID, long employeeID) throws AlreadyExistsException {
        Optional<Job> job = jobDao.getJobById(jobID);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
        if (employee.isPresent() && job.isPresent()) {
            Boolean exists = applicantDao.existsApplicant(employee.get(), job.get());
            if (exists)
                throw new AlreadyExistsException("You already applied for this job");
            return applicantDao.create(job.get(), employee.get());
        }
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Applicant> getAppliedJobsByApplicant(long employeeID, Long page, int pageSize) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
        if (employee.isPresent()) {
            return applicantDao.getAppliedJobsByApplicant(employee.get(), page, pageSize);
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
    public void apply(long jobID, long employeeID) throws AlreadyExistsException {
        Optional<Job> job = jobDao.getJobById(jobID);
        if (job.isPresent()) {
            Optional<Employee> employee = employeeDao.getEmployeeById(employeeID);
            Employer employer = job.get().getEmployerId();
            employer.firstWordsToUpper();
            String title = job.get().firstWordsToUpper();
            if (employee.isPresent()) {
                String name = employee.get().firstWordsToUpper();
                mailingService.sendApplyMail(employer.getId().getEmail(), title, name, jobID);
            }
            create(jobID, employeeID);
        }
    }

    @Transactional
    @Override
    public int changeStatus(int status, long employeeId, long jobId) {
        Optional<Job> job = jobDao.getJobById(jobId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if (job.isPresent() && employee.isPresent())
            return applicantDao.changeStatus(status, employee.get(), job.get());
        return -1;
    }

    @Transactional
    @Override
    public int getStatus(long employeeId, long jobId) {
        Optional<Job> job = jobDao.getJobById(jobId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if (job.isPresent() && employee.isPresent()) {
            if (applicantDao.existsApplicant(employee.get(), job.get()))
                return applicantDao.getStatus(employee.get(), job.get());
        }
        return -1;
    }

    @Transactional
    @Override
    public void withdrawApplication(long employeeId, long jobId) {
        Optional<Job> job = jobDao.getJobById(jobId);
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        if (job.isPresent() && employee.isPresent()) {
            Optional<Applicant> applicant = applicantDao.getApplicant(employee.get(), job.get());
            applicant.ifPresent(value -> applicantDao.deleteApplication(value));
        }
    }

    @Transactional
    @Override
    public void withdrawAppsFromJob(Optional<Job> job) {
        job.ifPresent(value -> applicantDao.deleteApplicationsFromJob(value));
    }

    @Override
    public void rejectAppsfromJob(Optional<Job> job) {
        job.ifPresent(value -> applicantDao.rejectApplications(value));
    }
}

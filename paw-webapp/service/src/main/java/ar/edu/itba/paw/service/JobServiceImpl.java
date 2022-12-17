package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Applicant;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.persistence.ApplicantDao;
import ar.edu.itba.paw.persistence.EmployeeDao;
import ar.edu.itba.paw.persistence.EmployerDao;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class JobServiceImpl implements JobService{
    @Autowired
    private JobDao jobDao;
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private EmployerDao employerDao;

    @Autowired
    private ApplicantService applicantService;

    @Transactional
    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description) {
        title = title.toLowerCase().trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        Optional<Employer> employer = employerDao.getEmployerById(employerId);
        if(employer.isPresent())
            return jobDao.create(title, location.toLowerCase(), employer.get(), availability, experienceYears, abilities, description);
        return null;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> getUserJobs(long employerID, Long page, long pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(employerID);
        if(employer.isPresent())
            return jobDao.getUserJobs(employer.get(), page, pageSize);
        return Collections.emptyList();
    }

    @Transactional(readOnly = true)
    @Override
    public Job getJobByID(long jobID) throws JobNotFoundException {
        Job job = jobDao.getJobById(jobID).orElseThrow(()-> new JobNotFoundException("job" + jobID + "does not exists"));
        return job;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> getFilteredJobs(String name, Long experienceYears, String location, String availability, String abilities, Long page, long pageSize) {
        if (name == null && experienceYears == null && location == null && availability == null && abilities == null && page == 0) {
            return jobDao.getAllActiveJobs(pageSize);
        }
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        List<String> locationList= new ArrayList<>();
        if (location != null) {
            locationList = Arrays.asList(location.split(","));
        }
        return jobDao.getFilteredJobs(name, experienceYears, locationList, availabilityList, abilitiesList, page, pageSize);
    }

    @Override
    public Boolean alreadyApplied(long jobId, long employeeId) {
        Optional<Employee> employee = employeeDao.getEmployeeById(employeeId);
        Optional<Job> job = jobDao.getJobById(jobId);
        if(employee.isPresent() && job.isPresent())
            return jobDao.alreadyApplied(job.get(), employee.get());
        return false;
    }

    @Transactional(readOnly = true)
    @Override
    public int getPageNumber(String name, Long experienceYears, String location, String availability, String abilities, long pageSize) {
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        List<String> locationList= new ArrayList<>();
        if (location != null) {
            locationList = Arrays.asList(location.split(","));
        }
        return jobDao.getPageNumber(name, experienceYears, locationList, availabilityList, abilitiesList, pageSize);
    }

    @Transactional
    @Override
    public void deleteJob(long jobId){
        jobDao.deleteJob(jobId);
    }

    @Transactional
    @Override
    public void closeJob(long jobId) {
        jobDao.closeJob(jobId);
        //TODO hay que pasarle la pagina
        List<Applicant> applicants = applicantService.getApplicantsByJob(jobId, 0L, 0);
        for(Applicant applicant : applicants){
            applicantService.changeStatus(2, applicant.getEmployeeID().getId().getId(), jobId);
        }
    }

    @Transactional
    @Override
    public void openJob(long jobId) {
        jobDao.openJob(jobId);
        //TODO paginacion
        List<Applicant> applicants = applicantService.getApplicantsByJob(jobId, 0L, 0);
        applicantService.withdrawAppsFromJob(applicants);
    }

    @Override
    public int getMyJobsPageNumber(long id, long pageSize) {
        Optional<Employer> employer = employerDao.getEmployerById(id);
        return employer.map(value -> jobDao.getMyJobsPageNumber(value, pageSize)).orElse(0);
    }
}

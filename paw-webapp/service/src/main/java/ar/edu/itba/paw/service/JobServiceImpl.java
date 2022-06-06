package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service

public class JobServiceImpl implements JobService{
    @Autowired
    private JobDao jobDao;

    @Transactional
    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description) {
        title = title.toLowerCase().trim().replaceAll(" +", " ");
        location = location.trim().replaceAll(" +", " ");
        return jobDao.create(title, location, employerId, availability, experienceYears, abilities, description);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Job> getUserJobs(Employer employerID) {
        return jobDao.getUserJobs(employerID);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Job> getJobByID(long jobID) {
        if(!jobDao.getJobById(jobID).isPresent())
            throw new JobNotFoundException("job" + jobID + "does not exists");
        Job job = jobDao.getJobById(jobID).get();
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(job.getAvailability().split(",")));
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(job.getAbilities().split(",")));
        Job aux = new Job(job.getTitle(), job.getLocation(), job.getJobId(), job.getEmployerId(), availabilityArr, job.getExperienceYears(), abilitiesArr, job.getDescription(), job.isOpened());
        return Optional.of(aux);
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
        return jobDao.getFilteredJobs(name, experienceYears, location, availabilityList, abilitiesList, page, pageSize);
    }

    @Override
    public Boolean alreadyApplied(long jobId, long employeeId) {
        return jobDao.alreadyApplied(jobId, employeeId);
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
        return jobDao.getPageNumber(name, experienceYears, location, availabilityList, abilitiesList, pageSize);
    }

    @Transactional(readOnly = true)
    @Override
    public String getJobNameById(long jobID) {
        return jobDao.getJobNameById(jobID);
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
    }

    @Transactional
    @Override
    public void openJob(long jobId) {
        jobDao.openJob(jobId);
    }
}

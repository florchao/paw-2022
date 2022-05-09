package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.exception.JobNotFoundException;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService{
    @Autowired
    JobDao jobDao;

    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description) {
        return jobDao.create(title, location, employerId, availability, experienceYears, abilities, description);
    }

    @Override
    public Optional<List<Job>> getUserJobs(long employerID) {
        return jobDao.getUserJobs(employerID);
    }

    @Override
    public Optional<Job> getJobByID(long jobID) {
        if(!jobDao.getJobById(jobID).isPresent())
            throw new JobNotFoundException("job" + jobID + "does not exists");
        Job job = jobDao.getJobById(jobID).get();
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(job.getAvailability().split(",")));
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(job.getAbilities().split(",")));
        Job aux = new Job(job.getTitle(), job.getLocation(), job.getJobId(), availabilityArr, job.getExperienceYears(), abilitiesArr, job.getDescription(), job.getEmployerName());
        return Optional.of(aux);
    }

    @Override
    public Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, String availability, String abilities, Long page, long pageSize) {
        if (name == null && experienceYears == null && location == null && availability == null && abilities == null && page == 0) {
            System.out.println("en all jobs");
            return jobDao.getAllJobs(pageSize);
        }
        List<String> availabilityList = new ArrayList<>();
        if (availability != null) {
            availabilityList = Arrays.asList(availability.split(","));
        }
        List<String> abilitiesList= new ArrayList<>();
        if (abilities != null) {
            abilitiesList = Arrays.asList(abilities.split(","));
        }
        System.out.println("en filtered jobs");
        return jobDao.getFilteredJobs(name, experienceYears, location, availabilityList, abilitiesList, page, pageSize);
    }

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

    @Override
    public String getJobNameById(long jobID) {
        return jobDao.getJobNameById(jobID);
    }
}

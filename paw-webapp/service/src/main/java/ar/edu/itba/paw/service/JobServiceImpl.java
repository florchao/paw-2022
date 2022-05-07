package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;
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
        Job job = jobDao.getJobById(jobID).get();
        List<String> availabilityArr = new ArrayList<>(Arrays.asList(job.getAvailability().split(",")));
        List<String> abilitiesArr = new ArrayList<>(Arrays.asList(job.getAbilities().split(",")));
        Job aux = new Job(job.getTitle(), job.getLocation(), job.getJobId(), availabilityArr, job.getExperienceYears(), abilitiesArr, job.getDescription(), job.getEmployerName());
        return Optional.of(aux);
    }
}

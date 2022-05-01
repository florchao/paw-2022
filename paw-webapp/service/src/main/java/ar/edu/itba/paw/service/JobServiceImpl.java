package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.persistence.JobDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

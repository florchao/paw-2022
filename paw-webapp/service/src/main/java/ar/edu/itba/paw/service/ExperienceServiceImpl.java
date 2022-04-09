package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.Experience;
import ar.edu.itba.paw.persistence.ExperienceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

@Service
public class ExperienceServiceImpl implements ExperienceService {

    @Autowired
    private ExperienceDao experienceDao;

    @Override
    public Optional<List<Experience>> getAllExperiences() {
        return experienceDao.getAllExperiences();
    }

    @Override
    public Experience create(long employeeId, String title, Time since, Time until, String description) {
        return experienceDao.create(employeeId, title, since, until, description);
    }
}

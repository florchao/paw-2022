package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Experience;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

public interface ExperienceDao {
    Optional<List<Experience>> getAllExperiences();

    Experience create(long employeeId, String title, Time since, Time until, String description);
}

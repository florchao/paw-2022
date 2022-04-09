package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Time;
import java.sql.Date;
import java.util.*;

@Repository
public class ExperienceJdbcDao implements ExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Experience> EXPERIENCE_ROW_MAPPER = (rs, rowNum) ->
            new Experience(rs.getLong("experiencesid"), rs.getLong("employeeid"), rs.getString("title"), rs.getDate("since"), rs.getDate("until"), rs.getString("description"));

    @Autowired
    public ExperienceJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Experience")
                .usingGeneratedKeyColumns("experiencesid");
    }

    @Override
    public Optional<List<Experience>> getAllExperiences() {
        List<Experience> query = jdbcTemplate.query("SELECT * FROM experiences", EXPERIENCE_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Experience create(long employeeId, String title, Date since, Date until, String description) {
        final Map<String, Object> experienceData = new HashMap<>();
        experienceData.put("employeeid", employeeId);
        experienceData.put("title", title);
        experienceData.put("since", since);
        experienceData.put("until", until);
        experienceData.put("description", description);

        int experiencesid = jdbcInsert.execute(experienceData);
        return new Experience(experiencesid, employeeId, title, since, until, description);
    }
}

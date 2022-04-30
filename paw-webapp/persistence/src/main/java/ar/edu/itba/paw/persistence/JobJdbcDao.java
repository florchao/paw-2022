package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JobJdbcDao implements JobDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("title"), rs.getString("location"),
                    rs.getLong("jobid"), rs.getLong("employerid"),
                    rs.getString("availability"), rs.getLong("experienceYears"),
                    rs.getString("abilities"));

    @Autowired
    public JobJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("job").usingGeneratedKeyColumns("jobid");
    }

    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities) {
        final Map<String, Object> jobData = new HashMap<>();
        jobData.put("employerid", employerId);
        jobData.put("title", title);
        jobData.put("location", location);
        jobData.put("availability", availability);
        jobData.put("experienceYears", experienceYears);
        jobData.put("abilities", abilities);
        //TODO: agregar desription desde el controller que me lo olvidee
        Number jobId = jdbcInsert.executeAndReturnKey(jobData);
        return new Job(title, location, jobId.longValue(), employerId, availability, experienceYears, abilities);
    }

    @Override
    public Optional<List<Job>> getUserJobs(long employerID) {
        List<Job> query = jdbcTemplate.query("SELECT * WHERE employerID = ?", new Object[] {employerID}, JOB_ROW_MAPPER);
        return Optional.of(query);
    }
}

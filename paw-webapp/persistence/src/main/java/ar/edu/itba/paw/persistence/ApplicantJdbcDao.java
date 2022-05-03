package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
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
public class ApplicantJdbcDao implements ApplicantDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Applicant> APPLICANT_ROW_MAPPER = (rs, rowNum) ->
            new Applicant(rs.getLong("jobID"), rs.getLong("employeeID"));

    @Autowired
    public ApplicantJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("applicants");
    }

    @Override
    public Applicant create(long jobID, long employeeID) {
        final Map<String, Object> jobData = new HashMap<>();
        jobData.put("employeeid", employeeID);
        jobData.put("jobid", jobID);
        jdbcInsert.execute(jobData);
        return new Applicant(jobID, employeeID);
    }

    @Override
    public Optional<List<Applicant>> getApplicantsByJob(long jobID) {
        List<Applicant> query = jdbcTemplate.query("SELECT * FROM applicants WHERE jobID = ?",
                new Object[] {jobID}, APPLICANT_ROW_MAPPER);
        return Optional.of(query);
    }
}

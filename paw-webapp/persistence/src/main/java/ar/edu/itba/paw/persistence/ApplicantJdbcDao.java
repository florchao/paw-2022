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

    private static final RowMapper<Applicant> APPLI_LIST_ROW_MAPPER = (rs, rowNum) ->
            new Applicant(rs.getLong("jobID"), rs.getLong("employeeID"), rs.getString("name"), rs.getString("email"));

    private  static final RowMapper<Applicant> INFO_ROW_MAPPER = (rs, rowNum) ->
            new Applicant(rs.getLong("jobID"), rs.getString("email"), rs.getString("title"));

    private static final RowMapper<Boolean> APPLICANT_EXISTS_ROW_MAPPER = (rs, rowNum) -> rs.getBoolean("exists");

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
        List<Applicant> query = jdbcTemplate.query("SELECT employeeid, jobid, name, email FROM applicants NATURAL " +
                        "JOIN employee JOIN users ON employee.employeeid = users.userid WHERE jobid = ?",
                new Object[] {jobID}, APPLI_LIST_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Optional<Applicant> getInfoMail(long jobID) {
        List<Applicant> query = jdbcTemplate.query("SELECT jobid, title, email FROM jobs JOIN users " +
                        "ON employerid = userid WHERE jobid = ?",
                new Object[] {jobID}, INFO_ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<Boolean> existsApplicant(long employeeId, long jobId) {
        List<Boolean> query = jdbcTemplate.query("SELECT EXISTS(SELECT * FROM applicants WHERE employeeid = ? AND jobid = ?)", new Object[] {employeeId,jobId}, APPLICANT_EXISTS_ROW_MAPPER);

        return query.stream().findFirst();
    }
}

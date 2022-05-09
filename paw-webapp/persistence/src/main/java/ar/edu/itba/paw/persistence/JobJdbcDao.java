package ar.edu.itba.paw.persistence;

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

    private static final RowMapper<Job> MY_JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("title"), rs.getString("location"),
                    rs.getLong("jobid"),
                    rs.getString("availability"), rs.getLong("experienceYears"),
                    rs.getString("abilities"), rs.getString("description"));

    private static final RowMapper<Job> JOB_ROW_MAPPER = (rs, rowNum) ->
            new Job(rs.getString("title"), rs.getString("location"),
                    rs.getLong("jobid"),
                    rs.getString("availability"), rs.getLong("experienceYears"),
                    rs.getString("abilities"), rs.getString("description"), rs.getString("name"));

    @Autowired
    public JobJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("jobs").usingGeneratedKeyColumns("jobid");
    }

    @Override
    public Job create(String title, String location, long employerId, String availability, long experienceYears, String abilities, String description) {
        final Map<String, Object> jobData = new HashMap<>();
        jobData.put("employerid", employerId);
        jobData.put("title", title);
        jobData.put("location", location);
        jobData.put("availability", availability);
        jobData.put("experienceYears", experienceYears);
        jobData.put("abilities", abilities);
        jobData.put("description", description);
        Number jobId = jdbcInsert.executeAndReturnKey(jobData);
        return new Job(title, location, jobId.longValue(), employerId, availability, experienceYears, abilities, description);
    }

    @Override
    public Optional<List<Job>> getUserJobs(long employerID) {
        List<Job> query = jdbcTemplate.query("SELECT * FROM jobs WHERE employerID = ?",
                new Object[] {employerID}, MY_JOB_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Optional<Job> getJobById(long jobId) {
        List<Job> query = jdbcTemplate.query("SELECT title, location, jobid, availability, experienceYears, " +
                        "abilities, description, name  FROM jobs NATURAL JOIN employer WHERE jobID = ?",
                new Object[] {jobId}, JOB_ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public Optional<List<Job>> getAllJobs(long pageSize) {
        List<Job> query = jdbcTemplate.query("SELECT * FROM jobs LIMIT " + pageSize, new Object[] {}, MY_JOB_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Optional<List<Job>> getFilteredJobs(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long page, long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM jobs where ");
        if (name != null) {
            stringBuilder.append("title like '%").append(name.toLowerCase()).append("%'");
            stringBuilder.append(" and ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("experienceYears >= ").append(experienceYears);
            stringBuilder.append(" and ");
        }
        if (location != null) {
            stringBuilder.append("location like '%").append(location.toLowerCase()).append("%' ");
            stringBuilder.append(" and ");
        }
        for (String ability : abilities) {
            stringBuilder.append("abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and ");
        }
        for (String av : availability) {
            stringBuilder.append("availability like '%").append(av).append("%'");
            stringBuilder.append(" or  ");
        }
        stringBuilder.setLength(stringBuilder.length() - 5);
        stringBuilder.append(" limit ").append(pageSize);
        stringBuilder.append(" offset ").append(page * pageSize);
        List<Job> query = jdbcTemplate.query(stringBuilder.toString(), new Object[] {}, MY_JOB_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public int getPageNumber(String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long pageSize) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT COUNT(*) FROM jobs where ");
        if (name != null) {
            stringBuilder.append("title like '%").append(name.toLowerCase()).append("%'");
            stringBuilder.append(" and ");
        }
        if (experienceYears != null && experienceYears.intValue() > 0) {
            stringBuilder.append("experienceYears >= ").append(experienceYears);
            stringBuilder.append(" and ");
        }
        if (location != null) {
            stringBuilder.append("location like '%").append(location.toLowerCase()).append("%' ");
            stringBuilder.append(" and ");
        }
        // TODO Aca iria lo mismo pero para experienceList
        for (String av : availability) {
            stringBuilder.append("availability like '%").append(av).append("%'");
            stringBuilder.append(" and ");
        }
        for (String ability : abilities) {
            stringBuilder.append("abilities like '%").append(ability).append("%'");
            stringBuilder.append(" and ");
        }
        stringBuilder.setLength(stringBuilder.length() - 5);
        // TODO Hacer que el limit no este hardcodeado
        String query = jdbcTemplate.queryForObject(stringBuilder.toString(), new Object[] {}, String.class);
        return (int) Math.ceil((float) Integer.parseInt(query) / pageSize);
    }

    @Override
    public String getJobNameById(long jobID) {
        List<String> query = jdbcTemplate.query("SELECT title FROM jobs WHERE jobid = ? ", new Object[] {jobID}, (rs, rowNum) -> rs.getString("title"));
        Optional<String> optional = query.stream().findFirst();
        return optional.orElse("Trabajo sin nombre");
    }


}

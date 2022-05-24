package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
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

public class EmployerJdbcDao implements EmployerDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertProfileImage;

    private static final RowMapper<Employer> EMPLOYER_ROW_MAPPER = (rs, rowNum) ->
            new Employer(rs.getString("name"),
                    rs.getLong("employerID"));

    @Autowired
    public EmployerJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("employer");
        jdbcInsertProfileImage = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images");

    }
    @Override
    public Employer create(String name, long id, byte[] image) {
        final Map<String, Object> employerData = new HashMap<>();
        employerData.put("employerID", id);
        employerData.put("name", name);
        final Map<String, Object> argsProfileImage = new HashMap<>();
        argsProfileImage.put("userId", id);
        argsProfileImage.put("image", image);
        jdbcInsertProfileImage.execute(argsProfileImage);
        jdbcInsert.execute(employerData);
        return new Employer(name, id);
    }

    @Override
    public Optional<Employer> getEmployerById(long id){
        List<Employer> query = jdbcTemplate.query("SELECT * FROM employer WHERE employerID = ?", new Object[] {id}, EMPLOYER_ROW_MAPPER);
        return query.stream().findFirst();
    }

}

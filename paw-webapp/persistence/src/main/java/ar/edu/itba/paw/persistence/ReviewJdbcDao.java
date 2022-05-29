package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Review;
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
public class ReviewJdbcDao implements ReviewDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Review> REVIEW_ROW_MAPPER = (rs, rowNum) ->
            new Review(rs.getLong("reviewid"), rs.getLong("employeeid"), rs.getLong("employerid"), rs.getString("review"), rs.getString("name"));

    @Autowired
    public ReviewJdbcDao(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Review")
                .usingGeneratedKeyColumns("reviewid");
    }

    @Override
    public Review create(long employeeId, long employerId, String review) {
        final Map<String, Object> reviewData = new HashMap<>();
        reviewData.put("employeeid", employeeId);
        reviewData.put("employerid", employerId);
        reviewData.put("review", review);
        Number reviewId = jdbcInsert.executeAndReturnKey(reviewData);
        return new Review(reviewId.longValue(), employeeId, employerId, review);
    }

    @Override
    public Optional<List<Review>> getAllReviews(long employeeId, Long id) {
        StringBuilder sqlQuery = new StringBuilder("SELECT reviewid, employeeid, employerid, review, name FROM review NATURAL JOIN employer WHERE employeeid = ");
        sqlQuery.append(employeeId);
        if(id != null)
            sqlQuery.append(" AND employerId <> ").append(id);
        System.out.println(sqlQuery);
        List<Review> query = jdbcTemplate.query(sqlQuery.toString(), new Object[] {}, REVIEW_ROW_MAPPER);
        return Optional.of(query);
    }

    @Override
    public Optional<Review> getMyReview(long employeeId, long id) {
        System.out.print("Employee ID: ");
        System.out.println(employeeId);
        System.out.print("My ID: ");
        System.out.println(id);
        List<Review> query = jdbcTemplate.query("SELECT reviewid, employeeid, employerid, review, name FROM review NATURAL JOIN employer WHERE employeeid = ? AND employerid = ?", new Object[] {employeeId, id}, REVIEW_ROW_MAPPER);
        return query.stream().findFirst();
    }
}

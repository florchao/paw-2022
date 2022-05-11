package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.User;
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
public class UserJdbcDao implements UserDao{

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final SimpleJdbcInsert jdbcInsertProfileImage;


    private static final RowMapper<User> ROW_MAPPER = (rs, rowNum) ->
            new User(rs.getLong("userid"), rs.getString("email"), rs.getString("password"), rs.getInt("role"));

    private static final RowMapper<Boolean> IMAGE_EXISTS_ROW_MAPPER = (rs, rowNum) -> rs.getBoolean("exists");

    @Autowired
    public UserJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("Users")
                .usingGeneratedKeyColumns("userid");
        jdbcInsertProfileImage = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_images");

    }

    @Override
    public Optional<User> getUserById(long id) {
        List<User> query = jdbcTemplate.query("SELECT * FROM Users WHERE userId = ?", new Object[] {id}, ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public User create(String username, String password, int role) {
        final Map<String, Object> userData = new HashMap<>();
        userData.put("email", username);
        userData.put("password", password);
        userData.put("role", role);
        Number userId = jdbcInsert.executeAndReturnKey(userData);
        return new User(userId.longValue(), username, password, role);
    }

    @Override
    public List<User> getAll(int page) {
        return jdbcTemplate.query("SELECT * FROM Users LIMIT 10 OFFSET ?", new Object[] {(page - 1) * 10}, ROW_MAPPER);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        List<User> query = jdbcTemplate.query("SELECT * FROM Users WHERE email = ?", new Object[] {username}, ROW_MAPPER);

        return query.stream().findFirst();
    }

    @Override
    public boolean update(String username, String password) {
        return jdbcTemplate.update("UPDATE users " +
                " SET password = ? " +
                " WHERE email = ?;", password, username) == 1;
    }

    @Override
    public Optional<byte[]> getProfileImage(Long userId) {
        Optional<Optional<byte[]>> query = jdbcTemplate.query("SELECT image FROM profile_images WHERE userId = ?",
                new Object[]{userId}, (rs, rowNumber) ->  Optional.ofNullable(rs.getBytes("image"))).stream().findFirst();
        return query.orElseGet(Optional::empty);
    }

    private Optional<Boolean> hasProfileImage(Long userId){
        List<Boolean> query =  jdbcTemplate.query("SELECT EXISTS(SELECT * FROM profile_images WHERE userid = ?)", new Object[]{userId}, IMAGE_EXISTS_ROW_MAPPER);
        return query.stream().findFirst();
    }

    @Override
    public boolean updateProfileImage(Long userId, byte[] image) {
        Optional<Boolean> hasImage = hasProfileImage(userId);
        if(hasImage.isPresent())
            if(hasImage.get()) {
                return jdbcTemplate.update("UPDATE profile_images " +
                        "SET image = ?" +
                        "WHERE userId = ?", image, userId) == 1;
            }
        final Map<String, Object> argsProfileImage = new HashMap<>();
        argsProfileImage.put("userId", userId);
        argsProfileImage.put("image", image);
        return jdbcInsertProfileImage.execute(argsProfileImage) == 1;
    }

}

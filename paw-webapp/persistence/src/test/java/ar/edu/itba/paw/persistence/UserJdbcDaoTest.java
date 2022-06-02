package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class UserJdbcDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 1;

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserJpaDao userJpaDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate(){
        final User user = userJpaDao.create(USERNAME, PASSWORD, ROLE);

        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getEmail());
        Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

    }

    @Test
    public void testGetById(){
        String query = "INSERT INTO users values(0,'Username', 'Password', 1)";
        jdbcTemplate.execute(query);

        final Optional<User> user = userJpaDao.getUserById(0);

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals(PASSWORD, user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testGetByUsername(){
        String query = "INSERT INTO users values(0,'Username', 'Password', 1)";
        jdbcTemplate.execute(query);

        final Optional<User> user = userJpaDao.getUserByUsername("Username");

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals(PASSWORD, user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testUpdate(){
        String query = "INSERT INTO users values(0,'Username', 'Password', 1)";
        jdbcTemplate.execute(query);

        userJpaDao.update("Username", "Password2");
        final Optional<User> user = userJpaDao.getUserById(0);

        Assert.assertNotNull(user);
        Assert.assertTrue(user.isPresent());
        Assert.assertEquals(USERNAME, user.get().getEmail());
        Assert.assertEquals("Password2", user.get().getPassword());
        Assert.assertEquals(ROLE, user.get().getRole());

    }

    @Test
    public void testGetAll(){

        String query = "INSERT INTO users values(0,'Username', 'Password', 1)";
        jdbcTemplate.execute(query);

        final List<User> list = userJpaDao.getAll(1);

        Assert.assertNotNull(list);
        Assert.assertEquals(1, list.size());
    }

}

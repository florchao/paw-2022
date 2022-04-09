package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.model.Employee;
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

import javax.sql.DataSource;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class UserJdbcDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";

    @Autowired
    DataSource dataSource;

    @Autowired
    private UserJdbcDao userJdbcDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void testCreate(){
        final User user = userJdbcDao.create(USERNAME);

        Assert.assertNotNull(user);
        Assert.assertEquals(USERNAME, user.getUsername());
        //Assert.assertEquals(PASSWORD, user.getPassword());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "users"));

    }

    @Test
    public void testGetById(){
        final Optional<User> user = userJdbcDao.getUserById(1);

        Assert.assertNotNull(user);
        Assert.assertFalse(user.isPresent());

    }


}

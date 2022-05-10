package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
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
public class EmployerJbcDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private EmployerJdbcDao employerJdbcDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "employer");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "profile_images");
    }

    private static final String NAME = "Name";
    private static final long ID = 1;
    private static final byte[] IMAGE = null;

    @Test
    public void testCreate(){
        final Employer employer = employerJdbcDao.create(NAME,ID, null);

        Assert.assertNotNull(employer);
        Assert.assertEquals(ID, employer.getId());
        Assert.assertEquals(NAME, employer.getName());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "employer"));

    }

    @Test
    public void testGetEmployeeById(){
        String query = "INSERT INTO employer values(1,'Name')";
        jdbcTemplate.execute(query);

        Optional<Employer> employer = employerJdbcDao.getEmployerById(1);
        Assert.assertNotNull(employer);
        Assert.assertTrue(employer.isPresent());
        Assert.assertEquals(NAME, employer.get().getName());

    }

}

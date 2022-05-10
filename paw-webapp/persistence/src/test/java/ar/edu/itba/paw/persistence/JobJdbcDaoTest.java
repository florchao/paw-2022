package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Job;
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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class JobJdbcDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobJdbcDao jobJdbcDao;

    private JdbcTemplate jdbcTemplate;

    private static final long ID = 1;
    private static final String TITLE = "Name";
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final String ABILITIES = "Abilities";
    private static final long EXPERIENCE_YEARS = 10;
    private static final String DESCRIPTION = "Description";

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "jobs");
    }

    @Test
    public void testCreate(){
        Job job = jobJdbcDao.create(TITLE, LOCATION, ID, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        Assert.assertNotNull(job);
        Assert.assertEquals(ID, job.getEmployerId());
        Assert.assertEquals(TITLE, job.getTitle());
        Assert.assertEquals(LOCATION, job.getLocation());
        Assert.assertEquals(AVAILABILITY, job.getAvailability());
        Assert.assertEquals(ABILITIES, job.getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, job.getExperienceYears());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "jobs"));

    }

    @Test
    public void testGetUserJobs(){
        String query = "INSERT INTO jobs values(0,1, 'Name', 'Location', 'Availability', 10, 'Abilities', 'Description')";
        jdbcTemplate.execute(query);

        Optional<List<Job>> job =  jobJdbcDao.getUserJobs(ID);

        Assert.assertNotNull(job);
        Assert.assertTrue(job.isPresent());
        Assert.assertEquals(1, job.get().size());
    }

    @Test
    public void testGetAllJobs(){
        String query = "INSERT INTO jobs values(0,1, 'Name', 'Location', 'Availability', 10, 'Abilities', 'Description')";
        jdbcTemplate.execute(query);

        Optional<List<Job>> job =  jobJdbcDao.getAllJobs(10);

        Assert.assertNotNull(job);
        Assert.assertTrue(job.isPresent());
        Assert.assertEquals(1, job.get().size());
    }

    @Test
    public void testGetJobNameById(){
        String query = "INSERT INTO jobs values(1,1, 'Name', 'Location', 'Availability', 10, 'Abilities', 'Description')";
        jdbcTemplate.execute(query);

        String name = jobJdbcDao.getJobNameById(ID);

        Assert.assertNotNull(name);
        Assert.assertEquals(TITLE, name);


    }
}

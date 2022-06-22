package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.Job;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class JobJpaDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private JobJpaDao jobJdbcDao;

    @Autowired
    private EmployerJpaDao employerJdbcDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @PersistenceContext
    private EntityManager em;

    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 2;

    private static final long ID = 0;
    private static final String TITLE = "name";
    private static final String LOCATION = "location";
    private static final String AVAILABILITY = "Availability";
    private static final String ABILITIES = "Abilities";
    private static final long EXPERIENCE_YEARS = 10;
    private static final String DESCRIPTION = "Description";

    private static final String NAME = "Name";
    @Test
    public void testCreate(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        final Employer employer = employerJdbcDao.create(NAME,user.get(), image);
        Job job = jobJdbcDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        Assert.assertNotNull(job);
        Assert.assertEquals(ID, job.getEmployerId().getId().getId());
        Assert.assertEquals(TITLE, job.getTitle());
        Assert.assertEquals(LOCATION, job.getLocation());
        Assert.assertEquals(AVAILABILITY, job.getAvailability());
        Assert.assertEquals(ABILITIES, job.getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, job.getExperienceYears());

    }

//    @Test
//    public void testGetFilteredJobsByExperienceYearsAndAbilities() {
//        String query = "INSERT INTO jobs values(1,1, 'First job', 'Location','Con cama', 5, 'Planchar,Cocinar', 'Description')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO jobs values(2,2, 'Second job', 'Location','Con cama,Jornada entera', 10, 'Cocinar', 'Description')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO jobs values(3,3, 'Third job', 'Location','Con cama,Jornada entera', 20, 'Cocinar', 'Description')";
//        jdbcTemplate.execute(query);
//
//        Optional<List<Job>> job =  jobJdbcDao.getFilteredJobs(null,10L,null, new ArrayList<>(), Arrays.asList("Cocinar"), 0L, 8);
//
//        Assert.assertNotNull(job);
//        Assert.assertTrue(job.isPresent());
//        Assert.assertEquals(2,job.get().get(0).getJobId());
//        Assert.assertEquals(3,job.get().get(1).getJobId());
//    }

    @Test
    public void testGetUserJobs(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        byte [] image = {};
        Employer employer = employerJdbcDao.create(NAME, user.get(), image );
        jobJdbcDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        List<Job> job =  jobJdbcDao.getUserJobs(employer, Long.valueOf(0), 2);

        Assert.assertFalse(job.isEmpty());
        Assert.assertEquals(1, job.size());
    }

    @Test
    public void testGetAllJobs(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        byte [] image = {};
        Employer employer = employerJdbcDao.create(NAME, user.get(), image );
        jobJdbcDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        List<Job> job =  jobJdbcDao.getAllActiveJobs(10);

        Assert.assertFalse(job.isEmpty());
        Assert.assertEquals(1, job.size());
    }

    @Test
    public void testDelete(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        byte [] image = {};
        Employer employer = employerJdbcDao.create(NAME, user.get(), image );
        Job job = jobJdbcDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        jobJdbcDao.deleteJob(job.getJobId());
        Optional<Job> jobaux = jobJdbcDao.getJobById(job.getJobId());

        Assert.assertFalse(jobaux.isPresent());
    }

}


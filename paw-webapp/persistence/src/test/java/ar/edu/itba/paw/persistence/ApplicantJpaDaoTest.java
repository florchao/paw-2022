package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
@ContextConfiguration(classes = ar.edu.itba.paw.persistence.TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class ApplicantJpaDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private ApplicantJpaDao applicantJpaDao;

    @Autowired
    private EmployerJpaDao employerJpaDao;

    @Autowired
    private UserJpaDao userJpaDao;
    @Autowired
    private JobJpaDao jobJpaDao;
    @Autowired
    private EmployeeJpaDao employeeJpaDao;
    @PersistenceContext
    private EntityManager em;

    private static final long EMPLOYEE_ID = 1;
    private static final long JOB_ID = 1;
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String USERNAME2 = "Username2";

    private static final int ROLE = 2;

    private static final int STATUS = 0;
    private static final int PENDING = 0;
    private static final int ACCEPTED = 1;
    private static final int REJECTED = 2;

    private static final String NAME = "Name";
    private static final String TITLE = "Name";
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final String ABILITIES = "Abilities";
    private static final long EXPERIENCE_YEARS = 10;
    private static final long HOURLY_FEE = 100;
    private static final String DESCRIPTION = "Description";


    @Test
    public void testCreate() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte[] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        final Employer employer = employerJpaDao.create(NAME, user.get(), image);
        Job job = jobJpaDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        Applicant applicant = applicantJpaDao.create(job, employee);

        Assert.assertNotNull(applicant);
        Assert.assertEquals(employee.getId().getId(), applicant.getEmployeeID().getId().getId());
        Assert.assertEquals(job.getJobId(), applicant.getJobID().getJobId());
    }

    @Test
    public void testGetJobsByApplicant() {
        byte[] image = {};
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        final List<Applicant> list = applicantJpaDao.getAppliedJobsByApplicant(employee, 0L, 1);
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testGetApplicantsByJob() {
        byte[] image = {};
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employer employer = employerJpaDao.create(NAME, user2, image);
        Job job = jobJpaDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);
        final List<Applicant> list = applicantJpaDao.getApplicantsByJob(job, 0L, 1);
        Assert.assertNotNull(list);
        Assert.assertEquals(0, list.size());
    }

    @Test
    public void testExistsApplicant() {
        byte[] image = {};
        User user1 = userJpaDao.create(USERNAME, PASSWORD, 1);
        User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        final Employer employer = employerJpaDao.create(NAME, user1, image);
        final Job job = jobJpaDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);
        final Applicant applicant = applicantJpaDao.create(job, employee);

        Boolean exists = applicantJpaDao.existsApplicant(employee, job);

        em.flush();

        Assert.assertNotNull(employee);
        Assert.assertNotNull(job);
        Assert.assertNotNull(applicant);
        Assert.assertNotNull(employer);
        Assert.assertTrue(exists);

    }

    @Test
    public void testGetStatus() {
        byte[] image = {};
        User user1 = userJpaDao.create(USERNAME, PASSWORD, 1);
        User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        final Employer employer = employerJpaDao.create(NAME, user1, image);
        final Job job = jobJpaDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);
        final Applicant applicant = applicantJpaDao.create(job, employee);

        int status = applicantJpaDao.getStatus(employee, job);

        em.flush();

        Assert.assertNotNull(employee);
        Assert.assertNotNull(job);
        Assert.assertNotNull(applicant);
        Assert.assertNotNull(employer);
        Assert.assertEquals(PENDING, status);
    }

    @Test
    public void testChangeStatus() {
        byte[] image = {};
        User user1 = userJpaDao.create(USERNAME, PASSWORD, 1);
        User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        final Employer employer = employerJpaDao.create(NAME, user1, image);
        final Job job = jobJpaDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);
        final Applicant applicant = applicantJpaDao.create(job, employee);
        em.flush();

        Assert.assertNotNull(employee);
        Assert.assertNotNull(job);
        Assert.assertNotNull(applicant);
        Assert.assertNotNull(employer);

        int accepted = applicantJpaDao.changeStatus(ACCEPTED, employee, job);
        Assert.assertEquals(ACCEPTED, accepted);
        int rejected = applicantJpaDao.changeStatus(REJECTED, employee, job);
        Assert.assertEquals(REJECTED, rejected);

    }
}





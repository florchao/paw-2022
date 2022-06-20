package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.*;
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

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class ApplicantJdbcDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private ApplicantJpaDao applicantJdbcDao;

    @Autowired
    private EmployerJpaDao employerJdbcDao;

    @Autowired
    private UserJpaDao userJpaDao;
    @Autowired
    private JobJpaDao jobJdbcDao;
    @Autowired
    private EmployeeJpaDao employeeJpaDao;
    @PersistenceContext
    private EntityManager em;

    private static final long EMPLOYEE_ID = 1;
    private static final long JOB_ID = 1;
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 2;
    private static final String NAME = "Name";
    private static final String TITLE = "Name";
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final String ABILITIES = "Abilities";
    private static final long EXPERIENCE_YEARS = 10;
    private static final String DESCRIPTION = "Description";


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
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
        final Employer employer = employerJdbcDao.create(NAME,user.get(), image);
        Job job = jobJdbcDao.create(TITLE, LOCATION, employer, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, DESCRIPTION);

        Applicant applicant = applicantJdbcDao.create(job, employee);

        Assert.assertNotNull(applicant);
        Assert.assertEquals(employee.getId().getId(), applicant.getEmployeeID().getId().getId());
        Assert.assertEquals(job.getJobId(), applicant.getJobID().getJobId());
    }
}





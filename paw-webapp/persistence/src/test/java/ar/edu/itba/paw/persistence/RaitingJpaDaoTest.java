package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class RaitingJpaDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String AVAILABILITY = "1";
    private static final long EXPERIENCE_YEARS = 10;
    private static final String ABILITIES = "4";
    @Autowired
    private EmployerJpaDao employerJdbcDao;

    @Autowired
    private UserJpaDao userJpaDao;
    @Autowired
    private EmployeeJpaDao employeeJpaDao;

    @Autowired
    private  RaitingJpaDao raitingJpaDao;

    private EntityManager em;

    @Test
    public void testhasAlreadyRated() {
        byte[] image = {};
        User user = userJpaDao.create(USERNAME, PASSWORD, 2);
        final Employer employer = employerJdbcDao.create(NAME,user, image);
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
        Assert.assertFalse(raitingJpaDao.hasAlreadyRated(employee, employer));
    }
}

package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ar.edu.itba.paw.persistence.TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class EmployeeJpaDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String USERNAME2 = "Username2";
    private static final String USERNAME3 = "Username3";

    private static final int ROLE = 1;
    private static final String NAME = "name";
    private static final String NAME2 = "d'nofrio";
    private static final String LOCATION = "4";
    private static final String AVAILABILITY = "1";
    private static final long ID = 0;
    private static final long EXPERIENCE_YEARS = 10;
    private static final long EXPERIENCE_YEARS2 = 20;

    private static final long HOURLY_FEE = 100;

    private static final String ABILITIES = "4";


    @Autowired
    public DataSource dataSource;

    @Autowired
    private EmployeeJpaDao employeeJpaDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreate() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        final Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);
        em.flush();

        Assert.assertEquals(ID, employee.getId().getId());
        Assert.assertEquals(NAME, employee.getName());
        Assert.assertEquals(LOCATION, employee.getLocation());
        Assert.assertEquals(AVAILABILITY, employee.getAvailability());
        Assert.assertEquals(ABILITIES, employee.getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, employee.getExperienceYears());

    }

    @Test
    public void testUpdate() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        byte[] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);

        employeeJpaDao.update(employee, "NameAux", "LocationAux", "AvailabilityAux", (long) 12, (long) 200, "AbilitiesAux");


        Assert.assertEquals("NameAux", employee.getName());
        Assert.assertEquals("LocationAux", employee.getLocation());
        Assert.assertEquals("AvailabilityAux", employee.getAvailability());
        Assert.assertEquals("AbilitiesAux", employee.getAbilities());
        Assert.assertEquals(12, employee.getExperienceYears());

    }

    @Test
    public void testGetEmployeeById() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        Optional<User> user = userJpaDao.getUserById(0);
        employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);


        Optional<Employee> employee = employeeJpaDao.getEmployeeById(0);
        Assert.assertNotNull(employee);
        Assert.assertTrue(employee.isPresent());
        Assert.assertEquals(NAME, employee.get().getName());
        Assert.assertEquals(LOCATION, employee.get().getLocation());
        Assert.assertEquals(AVAILABILITY, employee.get().getAvailability());
        Assert.assertEquals(ABILITIES, employee.get().getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, employee.get().getExperienceYears());


    }

    @Test
    public void testIsEmployee() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);


        Boolean ans = employeeJpaDao.isEmployee(employee);
        Assert.assertTrue(ans);
    }

    @Test
    public void testGetRating() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);


        employeeJpaDao.getPrevRating(employee);
        em.flush();

        Assert.assertEquals(0, employee.getRating(), 0);
    }

    @Test
    public void testGetRatingCount() {
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1, 0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES);


        employeeJpaDao.getRatingVoteCount(employee);
        em.flush();

        Assert.assertEquals(0, employee.getVoteCount(), 0);
    }

}




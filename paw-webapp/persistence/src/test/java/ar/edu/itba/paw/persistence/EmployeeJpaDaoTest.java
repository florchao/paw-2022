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
@ContextConfiguration(classes = TestConfig.class)
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

    private static final byte[] IMAGE = null;

    @Autowired
    public DataSource dataSource;

    @Autowired
    private EmployeeJpaDao employeeJpaDao;

    @Autowired
    private UserJpaDao userJpaDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreate(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        Optional<User> user = userJpaDao.getUserById(0);
        final Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, IMAGE);
        em.flush();

        Assert.assertEquals(ID, employee.getId().getId());
        Assert.assertEquals(NAME, employee.getName());
        Assert.assertEquals(LOCATION, employee.getLocation());
        Assert.assertEquals(AVAILABILITY, employee.getAvailability());
        Assert.assertEquals(ABILITIES, employee.getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, employee.getExperienceYears());

    }

    @Test
    public void testUpdate(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);

        employeeJpaDao.update(employee, "NameAux",  "LocationAux", "AvailabilityAux", (long) 12, (long) 200, "AbilitiesAux", image);


        Assert.assertEquals("NameAux", employee.getName());
        Assert.assertEquals("LocationAux", employee.getLocation());
        Assert.assertEquals("AvailabilityAux", employee.getAvailability());
        Assert.assertEquals("AbilitiesAux", employee.getAbilities());
        Assert.assertEquals(12, employee.getExperienceYears());

    }

    @Test
    public void testGetEmployees(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);

        List<Employee> list = employeeJpaDao.getEmployees(10);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testGetFilteredEmployees() {
        byte[] image = {};
        User user = userJpaDao.create(USERNAME, PASSWORD, ROLE);
        User user2 = userJpaDao.create(USERNAME2, PASSWORD, ROLE);
        User user3 = userJpaDao.create(USERNAME3, PASSWORD, ROLE);

        Employee employee = employeeJpaDao.create(user, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS2, HOURLY_FEE, ABILITIES, image);

        Employee employee2 = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);

        Employee employee3 = employeeJpaDao.create(user3, NAME2, "Almagro", AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);
        //String name, Long experienceYears, String location, List<String> availability, List<String> abilities, Long page, long pageSize, String orderCriteria
        List<Employee> list = employeeJpaDao.getFilteredEmployees(NAME, EXPERIENCE_YEARS, new ArrayList<>(Arrays.asList(LOCATION.split(","))), new ArrayList<>(Arrays.asList(AVAILABILITY.split(","))), new ArrayList<>(Arrays.asList(ABILITIES.split(","))), 0L, 2, "experienceYears");

        List<Employee> list2 = employeeJpaDao.getFilteredEmployees(NAME2, EXPERIENCE_YEARS, null, new ArrayList<>(Arrays.asList(AVAILABILITY.split(","))), new ArrayList<>(Arrays.asList(ABILITIES.split(","))), 0L, 2, "experienceYears");

        Assert.assertNotNull(list);
        Assert.assertEquals(user.getId() ,list.get(0).getId().getId());
        Assert.assertEquals(user2.getId(),list.get(1).getId().getId());
        Assert.assertEquals(2,list.size());
        Assert.assertTrue(list.get(0).getExperienceYears() >= list.get(1).getExperienceYears());

        Assert.assertNotNull(list2);
        Assert.assertEquals(user3.getId() ,list2.get(0).getId().getId());
        Assert.assertEquals(1,list2.size());
    }

    @Test
    public void testGetEmployeeById(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();

        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS,HOURLY_FEE, ABILITIES, image);


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
    public void testIsEmployee(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);


        Boolean ans = employeeJpaDao.isEmployee(employee);
        Assert.assertTrue(ans);
    }

    @Test
    public void testGetRating(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE,  ABILITIES, image);


        employeeJpaDao.getPrevRating(employee);
        em.flush();

        Assert.assertEquals(0, employee.getRating(), 0);
    }

    @Test
    public void testGetRatingCount(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, HOURLY_FEE, ABILITIES, image);


        employeeJpaDao.getRatingVoteCount(employee);
        em.flush();

        Assert.assertEquals(0, employee.getVoteCount(), 0);
    }


}




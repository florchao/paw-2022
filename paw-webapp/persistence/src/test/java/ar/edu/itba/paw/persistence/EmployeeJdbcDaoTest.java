package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;
import sun.security.x509.AVA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import javax.swing.text.html.Option;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
@Rollback
public class EmployeeJdbcDaoTest {
    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final int ROLE = 1;
    private static final String NAME = "Name";
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final long ID = 0;
    private static final long EXPERIENCE_YEARS = 10;
    private static final String ABILITIES = "Abilities";

    private static final byte[] IMAGE = null;

    @Autowired
    DataSource dataSource;

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

        final Employee employee = employeeJpaDao.create(ID, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, IMAGE);
        em.flush();

        Assert.assertNotNull(employee);
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
        Employee employee = employeeJpaDao.create(0, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);

        employeeJpaDao.update(employee, "NameAux",  "LocationAux", "AvailabilityAux", (long) 12, "AbilitiesAux", image);


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
        Employee employee = employeeJpaDao.create(0, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);

        List<Employee> list = employeeJpaDao.getEmployees(10);

        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1, list.size());
    }

//    @Test
//    public void testGetFilteredEmployeesByLocation() {
//        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
//                .setParameter(1,0)
//                .setParameter(2, USERNAME)
//                .setParameter(3, PASSWORD)
//                .setParameter(4, ROLE)
//                .executeUpdate();
//
//        byte [] image = {};
//        Employee employee = employeeJpaDao.create(0, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
//
//        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
//                .setParameter(1,1)
//                .setParameter(2, USERNAME)
//                .setParameter(3, PASSWORD)
//                .setParameter(4, ROLE)
//                .executeUpdate();
//
//        byte [] image2 = {};
//        Employee employee2 = employeeJpaDao.create(1, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
//
//        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
//                .setParameter(1,2)
//                .setParameter(2, USERNAME)
//                .setParameter(3, PASSWORD)
//                .setParameter(4, ROLE)
//                .executeUpdate();
//
//        byte [] image3 = {};
//        Employee employee3 = employeeJpaDao.create(2, NAME, "Almargo", AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
//
//        Optional<List<Employee>> list = employeeJpaDao.getFilteredEmployees(null, 0L, "Almagro", null, null, 0L, 2);
//
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.isPresent());
//        Assert.assertEquals(1,list.get().get(0).getId());
//        Assert.assertEquals(3,list.get().get(1).getId());
//    }
//
//    @Test
//    public void testGetFilteredEmployeesByExperienceYearsAndName() {
//        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 0, 'Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(4,'Luis', 'Villa Crespo', 'Jornada completa', 20, 'Cocinar')";
//        jdbcTemplate.execute(query);
//
//        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees("Luis", 5L, null, null, new ArrayList<>(), new ArrayList<>(), 0L, 2);
//
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.isPresent());
//        Assert.assertEquals(1,list.get().get(0).getId());
//        Assert.assertEquals(4,list.get().get(1).getId());
//    }
//
//    @Test
//    public void testGetFilteredEmployeesByAbilitiesAndNameAndExperienceYears() {
//        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar,Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Cocinar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 10, 'Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(4,'Luis', 'Villa Crespo', 'Jornada completa', 20, 'Cocinar,Planchar')";
//        jdbcTemplate.execute(query);
//
//        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees("Luis", 5L, null, null, new ArrayList<>(), Arrays.asList("Planchar"), 0L, 4);
//
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.isPresent());
//        Assert.assertEquals(1,list.get().get(0).getId());
//        Assert.assertEquals(4,list.get().get(1).getId());
//    }
//
//    @Test
//    public void testGetFilteredEmployeesByAvailability() {
//        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 10, 'Planchar')";
//        jdbcTemplate.execute(query);
//        query = "INSERT INTO employee values(4,'Santiago', 'Villa Crespo', 'Jornada completa', 10, 'Cocinar')";
//        jdbcTemplate.execute(query);
//
//        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees(null, 0L, null, null, Arrays.asList("Jornada completa"), new ArrayList<>(), 0L, 8);
//
//        Assert.assertNotNull(list);
//        Assert.assertTrue(list.isPresent());
//        Assert.assertEquals(1,list.get().get(0).getId());
//        Assert.assertEquals(3,list.get().get(1).getId());
//        Assert.assertEquals(4,list.get().get(2).getId());
//    }
//
//    @Test
//    public void testGetEmployeeById(){
//        String query = "INSERT INTO employee values(1,'Name', 'Location', 'Availability', 10, 'Abilities')";
//        jdbcTemplate.execute(query);
//
//        Optional<Employee> employee = employeeJdbcDao.getEmployeeById(1);
//        Assert.assertNotNull(employee);
//        Assert.assertTrue(employee.isPresent());
//        Assert.assertEquals(NAME, employee.get().getName());
//        Assert.assertEquals(LOCATION, employee.get().getLocation());
//        Assert.assertEquals(AVAILABILITY, employee.get().getAvailability());
//        Assert.assertEquals(ABILITIES, employee.get().getAbilities());
//        Assert.assertEquals(EXPERIENCE_YEARS, employee.get().getExperienceYears());
//
//
//    }
}




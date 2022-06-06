package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Experience;
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

import javax.sql.DataSource;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

    /*
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class EmployeeJdbcDaoTest {

    private static final String NAME = "Name";
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final long ID = 1;
    private static final long EXPERIENCE_YEARS = 10;
    private static final String ABILITIES = "Abilities";

    private static final byte[] IMAGE = null;

    @Autowired
    DataSource dataSource;

    @Autowired
    private EmployeeJpaDao employeeJdbcDao;

    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "employee");
    }

    @Test
    public void testCreate(){
        final Employee employee = employeeJdbcDao.create(ID, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, IMAGE);

        Assert.assertNotNull(employee);
        Assert.assertEquals(ID, employee.getId());
        Assert.assertEquals(NAME, employee.getName());
        Assert.assertEquals(LOCATION, employee.getLocation());
        Assert.assertEquals(AVAILABILITY, employee.getAvailability());
        Assert.assertEquals(ABILITIES, employee.getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, employee.getExperienceYears());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "employee"));

    }

    @Test
    public void testUpdate(){
        String query = "INSERT INTO employee values(1,'Name', 'Location', 'Availability', 10, 'Abilities')";
        jdbcTemplate.execute(query);

        employeeJdbcDao.update(ID, "NameAux",  "LocationAux", "AvailabilityAux", 12, "AbilitiesAux");
        final Optional<Employee> employee = employeeJdbcDao.getEmployeeById(ID);

        Assert.assertNotNull(employee);
        Assert.assertTrue(employee.isPresent());
        Assert.assertEquals("NameAux", employee.get().getName());
        Assert.assertEquals("LocationAux", employee.get().getLocation());
        Assert.assertEquals("AvailabilityAux", employee.get().getAvailability());
        Assert.assertEquals("AbilitiesAux", employee.get().getAbilities());
        Assert.assertEquals(12, employee.get().getExperienceYears());

    }

    @Test
    public void testGetEmployees(){
        String query = "INSERT INTO employee values(1,'Name', 'Location', 'Availability', 10, 'Abilities')";
        jdbcTemplate.execute(query);

        Optional<List<Employee>> list = employeeJdbcDao.getEmployees(10);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.isPresent());
        Assert.assertEquals(1, list.get().size());
    }

    @Test
    public void testGetFilteredEmployeesByLocation() {
        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 10, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(4,'Santiago', 'Villa Crespo', 'Jornada completa', 10, 'Cocinar')";
        jdbcTemplate.execute(query);

        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees(null, 0L, "Almagro", null, new ArrayList<>(), new ArrayList<>(), 0L, 2);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.isPresent());
        Assert.assertEquals(1,list.get().get(0).getId());
        Assert.assertEquals(3,list.get().get(1).getId());
    }

    @Test
    public void testGetFilteredEmployeesByExperienceYearsAndName() {
        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 0, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(4,'Luis', 'Villa Crespo', 'Jornada completa', 20, 'Cocinar')";
        jdbcTemplate.execute(query);

        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees("Luis", 5L, null, null, new ArrayList<>(), new ArrayList<>(), 0L, 2);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.isPresent());
        Assert.assertEquals(1,list.get().get(0).getId());
        Assert.assertEquals(4,list.get().get(1).getId());
    }

    @Test
    public void testGetFilteredEmployeesByAbilitiesAndNameAndExperienceYears() {
        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar,Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Cocinar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 10, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(4,'Luis', 'Villa Crespo', 'Jornada completa', 20, 'Cocinar,Planchar')";
        jdbcTemplate.execute(query);

        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees("Luis", 5L, null, null, new ArrayList<>(), Arrays.asList("Planchar"), 0L, 4);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.isPresent());
        Assert.assertEquals(1,list.get().get(0).getId());
        Assert.assertEquals(4,list.get().get(1).getId());
    }

    @Test
    public void testGetFilteredEmployeesByAvailability() {
        String query = "INSERT INTO employee values(1,'Luis', 'Almagro', 'Media jornada,Jornada completa', 10, 'Cocinar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(2,'Jose', 'Lanus', 'Media jornada', 5, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(3,'Tomas', 'Almagro', 'Con cama,Jornada completa', 10, 'Planchar')";
        jdbcTemplate.execute(query);
        query = "INSERT INTO employee values(4,'Santiago', 'Villa Crespo', 'Jornada completa', 10, 'Cocinar')";
        jdbcTemplate.execute(query);

        Optional<List<Employee>> list = employeeJdbcDao.getFilteredEmployees(null, 0L, null, null, Arrays.asList("Jornada completa"), new ArrayList<>(), 0L, 8);

        Assert.assertNotNull(list);
        Assert.assertTrue(list.isPresent());
        Assert.assertEquals(1,list.get().get(0).getId());
        Assert.assertEquals(3,list.get().get(1).getId());
        Assert.assertEquals(4,list.get().get(2).getId());
    }

    @Test
    public void testGetEmployeeById(){
        String query = "INSERT INTO employee values(1,'Name', 'Location', 'Availability', 10, 'Abilities')";
        jdbcTemplate.execute(query);

        Optional<Employee> employee = employeeJdbcDao.getEmployeeById(1);
        Assert.assertNotNull(employee);
        Assert.assertTrue(employee.isPresent());
        Assert.assertEquals(NAME, employee.get().getName());
        Assert.assertEquals(LOCATION, employee.get().getLocation());
        Assert.assertEquals(AVAILABILITY, employee.get().getAvailability());
        Assert.assertEquals(ABILITIES, employee.get().getAbilities());
        Assert.assertEquals(EXPERIENCE_YEARS, employee.get().getExperienceYears());


    }
}
     */




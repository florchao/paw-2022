package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
import ar.edu.itba.paw.model.Employee;
import ar.edu.itba.paw.model.Employer;
import ar.edu.itba.paw.model.User;
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
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
@Transactional
public class ContactJpaDaoTest {
    @Autowired
    private DataSource dataSource;

    @Autowired
    private ContactJpaDao contactJpaDao;

    @PersistenceContext
    private EntityManager em;

    private static final String PASSWORD = "Password";
    private static final String USERNAME = "Username";
    private static final String USERNAME2 = "Username2";
    private static final String USERNAME3 = "Username3";

    private static final int ROLE = 2;

    private static final long EXPERIENCE_YEARS = 10;
    private static final String ABILITIES = "Abilities";

    @Autowired
    private EmployerJpaDao employerJdbcDao;

    @Autowired
    private UserJpaDao userJpaDao;
    @Autowired
    private EmployeeJpaDao employeeJpaDao;

    private static final long EMPLOYEE_ID = 1;
    private static final long EMPLOYER_ID = 1;
    private static final String LOCATION = "Location";
    private static final String AVAILABILITY = "Availability";
    private static final Date DATE = new Date(10);
    private static final String MESSAGE = "Message";
    private static final String PHONE = "Phone";
    private static final String NAME = "Name";

    @Test
    public void testCreate(){
        byte[] image = {};
        User user = userJpaDao.create(USERNAME, PASSWORD, ROLE);
        final Employer employer = employerJdbcDao.create(NAME,user, image);
        User user2 = userJpaDao.create(USERNAME, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);

        final Contact contact = contactJpaDao.create(employee, employer, DATE,MESSAGE, PHONE );

        Assert.assertNotNull(contact);
        Assert.assertEquals(DATE, contact.getCreated());
        Assert.assertEquals(MESSAGE, contact.getMessage());
        Assert.assertEquals(PHONE, contact.getPhoneNumber());

    }

    @Test
    public void testGetAllContacts(){
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,0)
                .setParameter(2, USERNAME)
                .setParameter(3, PASSWORD)
                .setParameter(4, ROLE)
                .executeUpdate();
        byte [] image = {};
        Optional<User> user = userJpaDao.getUserById(0);
        final Employer employer = employerJdbcDao.create(NAME,user.get(), image);
        em.createNativeQuery("INSERT INTO users VALUES (?,?,?,?)")
                .setParameter(1,1)
                .setParameter(2, "Employee")
                .setParameter(3, PASSWORD)
                .setParameter(4, 1)
                .executeUpdate();

        final Employee employee = employeeJpaDao.create(user.get(), NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);
        contactJpaDao.create(employee, employer, DATE,MESSAGE, PHONE );

        List<Contact> list = contactJpaDao.getAllContacts(employee, 0L, 2);
        Assert.assertFalse(list.isEmpty());
        Assert.assertEquals(1, list.size());
    }

    @Test
    public void testExistsContact(){
        byte[] image = {};
        User user = userJpaDao.create(USERNAME, PASSWORD, ROLE);
        final Employer employer = employerJdbcDao.create(NAME,user, image);
        User user3 = userJpaDao.create(USERNAME3, PASSWORD, ROLE);
        final Employer employer2 = employerJdbcDao.create(NAME,user3, image);
        User user2 = userJpaDao.create(USERNAME2, PASSWORD, 1);
        final Employee employee = employeeJpaDao.create(user2, NAME, LOCATION, AVAILABILITY, EXPERIENCE_YEARS, ABILITIES, image);

        final Contact contact = contactJpaDao.create(employee, employer, DATE,MESSAGE, PHONE );

        Boolean hasToExist = contactJpaDao.existsContact(employee, employer);
        Assert.assertTrue(hasToExist);
        Boolean hasToNotExist = contactJpaDao.existsContact(employee, employer2);
        Assert.assertFalse(hasToNotExist);


    }
}



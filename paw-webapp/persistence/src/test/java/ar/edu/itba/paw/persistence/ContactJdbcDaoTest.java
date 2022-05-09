package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Contact;
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
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ContactJdbcDaoTest {
    @Autowired
    DataSource dataSource;

    @Autowired
    private ContactJdbcDao contactJdbcDao;

    private JdbcTemplate jdbcTemplate;

    private static final long EMPLOYEE_ID = 1;
    private static final long EMPLOYER_ID = 1;
    private static final Date DATE = new Date(10);
    private static final String MESSAGE = "Message";
    private static final String PHONE = "Phone";

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "contact");
    }

    @Test
    public void testCreate(){
        Contact contact = contactJdbcDao.create(EMPLOYEE_ID, EMPLOYER_ID, DATE,MESSAGE, PHONE );

        Assert.assertNotNull(contact);
        Assert.assertEquals(EMPLOYEE_ID, contact.getEmployeeID());
        Assert.assertEquals(EMPLOYER_ID, contact.getEmployerID());
        Assert.assertEquals(DATE, contact.getCreated());
        Assert.assertEquals(MESSAGE, contact.getMessage());
        Assert.assertEquals(PHONE, contact.getPhoneNumber());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "contact"));

    }


}

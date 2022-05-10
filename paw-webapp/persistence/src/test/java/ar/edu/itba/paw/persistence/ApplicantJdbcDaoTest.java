package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Applicant;
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
import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class ApplicantJdbcDaoTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    private ApplicantJdbcDao applicantJdbcDao;

    private JdbcTemplate jdbcTemplate;

    private static final long EMPLOYEE_ID = 1;
    private static final long JOB_ID = 1;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(dataSource);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "applicants");
    }

    @Test
    public void testCreate(){
        Applicant applicant = applicantJdbcDao.create(JOB_ID, EMPLOYEE_ID);

        Assert.assertNotNull(applicant);
        Assert.assertEquals(EMPLOYEE_ID, applicant.getEmployeeID());
        Assert.assertEquals(JOB_ID, applicant.getJobID());
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, "applicants"));
    }

}
